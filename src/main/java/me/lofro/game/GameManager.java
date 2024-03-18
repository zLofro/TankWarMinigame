package me.lofro.game;

import lombok.Getter;
import lombok.Setter;
import me.lofro.TankWarMinigame;
import me.lofro.game.data.interfaces.Restorable;
import me.lofro.game.events.TankSpawnEvent;
import me.lofro.game.listeners.GameListeners;
import me.lofro.utils.ListenerUtils;
import me.lofro.utils.data.JsonConfig;
import me.lofro.utils.infinitepotion.InfinitePotionEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class GameManager implements Restorable {

    private final TankWarMinigame main;

    private @Getter GameData gameData;

    private @Getter @Setter boolean running;

    private final GameListeners gameListeners;

    public GameManager(final TankWarMinigame main) {
        this.main = main;
        this.gameListeners = new GameListeners(this);

        TankWarMinigame.getInstance().getDataManager().restore();
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TankWarMinigame.getGson().toJsonTree(gameData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.gameData = new GameData();
        } else {
            this.gameData = TankWarMinigame.getGson().fromJson(jsonConfig.getJsonObject(), GameData.class);
        }
    }

    public void startGame() {
        this.running = true;

        ListenerUtils.registerListener(gameListeners);

        preparePlayers(gameData.getParticipants());
    }

    public void stopGame() {
        this.running = false;

        ListenerUtils.unregisterListener(gameListeners);

        this.gameData.getDisplayEntities().forEach((playerID, displayID) -> {
            var display = Bukkit.getEntity(displayID);

            if (display != null) display.remove();

            var player = Bukkit.getPlayer(playerID);

            if (player != null) {
                InfinitePotionEffect.remove(player, PotionEffectType.INVISIBILITY);
                InfinitePotionEffect.remove(player, PotionEffectType.WEAKNESS);
                InfinitePotionEffect.remove(player, PotionEffectType.DAMAGE_RESISTANCE);
                InfinitePotionEffect.remove(player, PotionEffectType.SATURATION);
                //TODO TELEPORT PLAYERS BACK TO SPAWN.
            }
        });

        this.gameData.getDisplayEntities().clear();
    }

    private void preparePlayers(List<UUID> players) {
        for (UUID uuid : players) {
            var player = Bukkit.getPlayer(uuid);

            if (player == null) continue;
            preparePlayer(player);
        }
    }

    private void preparePlayer(Player player) {
        InfinitePotionEffect.create(player, PotionEffectType.INVISIBILITY, 0, false, false);
        InfinitePotionEffect.create(player, PotionEffectType.WEAKNESS, 100, false, false);
        InfinitePotionEffect.create(player, PotionEffectType.DAMAGE_RESISTANCE, 100, false, false);
        InfinitePotionEffect.create(player, PotionEffectType.SATURATION, 200, false, false);

        teleportRandomly(player, gameData.getBlocksInsideMap()); //last
    }

    private void teleportRandomly(List<UUID> players) {
        for (UUID uuid : players) {
            var player = Bukkit.getPlayer(uuid);

            if (player == null) continue;
            teleportRandomly(Bukkit.getPlayer(uuid), gameData.getBlocksInsideMap());
        }
    }

    private void teleportRandomly(Player player, List<Location> map) {
        Collections.shuffle(map);
        for (Location location : map) {
            if (!location.getBlock().getRelative(BlockFace.UP).getType().equals(Material.AIR)) continue;

            player.teleport(location);
            Bukkit.getPluginManager().callEvent(new TankSpawnEvent(player));
            break;
        }
    }

}
