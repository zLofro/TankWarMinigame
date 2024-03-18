package me.lofro.game.data;

import lombok.Getter;
import me.lofro.TankWarMinigame;
import me.lofro.utils.data.JsonConfig;

public class DataManager {

    private final @Getter JsonConfig gameData;

    public DataManager(final TankWarMinigame main) throws Exception {
        this.gameData = JsonConfig.config("gameData.json", main);
    }

    public void save() {
        TankWarMinigame.getInstance().getGameManager().save(this.gameData);
    }

    public void restore() {
        TankWarMinigame.getInstance().getGameManager().restore(this.gameData);
    }

}
