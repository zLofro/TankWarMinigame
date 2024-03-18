package me.lofro.game.listeners;

import me.lofro.game.GameManager;
import me.lofro.game.events.TankDeathEvent;
import me.lofro.game.events.TankSpawnEvent;
import me.lofro.utils.text.HexFormatter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Display;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameListeners implements Listener {

    private final GameManager gameManager;

    public GameListeners(final GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    private void onTankSpawn(TankSpawnEvent event) {
        var player = event.getController();

        var gameData = gameManager.getGameData();

        Display displayEntity;

        if (!gameData.getDisplayEntities().containsKey(player.getUniqueId())) {
            displayEntity = player.getWorld().createEntity(player.getLocation(), Display.class);
            gameManager.getGameData().getDisplayEntities().put(player.getUniqueId(), displayEntity.getUniqueId());

            displayEntity.customName(HexFormatter.hexFormat(player.getName()));
        } else {
            displayEntity = (Display) Bukkit.getEntity(gameManager.getGameData().getDisplayEntities().get(player.getUniqueId()));
        }
    }

    @EventHandler
    private void onTankDeath(TankDeathEvent event) {
        var player = event.getController();

        var displayEntity = (Display) Bukkit.getEntity(gameManager.getGameData().getDisplayEntities().get(player.getUniqueId()));

        if (displayEntity == null) return;

        displayEntity.remove();
        gameManager.getGameData().getDisplayEntities().remove(player.getUniqueId());
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        var player = event.getPlayer();
        var location = event.getTo();

        if (gameManager.getGameData().getParticipants().contains(player.getUniqueId()) && gameManager.isRunning()) {
            var displayEntity = Bukkit.getEntity(gameManager.getGameData().getDisplayEntities().get(player.getUniqueId()));

            if (displayEntity == null) return;

            displayEntity.getLocation().set(location.getX(), location.getY(), location.z());
            displayEntity.getLocation().setPitch(location.getPitch());
            displayEntity.getLocation().setYaw(location.getYaw());
            displayEntity.getLocation().setDirection(location.getDirection());
        }
    }

}
