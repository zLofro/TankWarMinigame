package me.lofro;

import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.lofro.game.GameManager;
import me.lofro.game.commands.GameCommand;
import me.lofro.game.data.DataManager;
import me.lofro.utils.CommandUtils;
import me.lofro.utils.data.adapters.LocationSerializer;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class TankWarMinigame extends JavaPlugin {

    private static @Getter TankWarMinigame instance;

    private @Getter GameManager gameManager;

    private @Getter DataManager dataManager;

    private @Getter static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationSerializer())
            .registerTypeAdapter(Location[].class, LocationSerializer.getArraySerializer())
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    @Override
    public void onEnable() {
        instance = this;

        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        try {
            this.dataManager = new DataManager(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.gameManager = new GameManager(this);

        paperCommandManager.getCommandCompletions().registerCompletion("@location", c -> ImmutableList.of("x,y,z"));

        CommandUtils.registerCommands(paperCommandManager, new GameCommand(this));
    }

    @Override
    public void onDisable() {
        this.dataManager.save();
    }

}
