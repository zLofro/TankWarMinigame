package me.lofro.game;

import lombok.Getter;
import lombok.Setter;
import me.lofro.utils.Locations;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.util.*;

public class GameData {

    private @Getter @Setter List<UUID> participants;

    private @Getter @Setter List<Location> blocksInsideMap;

    private @Getter Location firstCorner;

    private @Getter Location secondCorner;

    private @Getter Map<UUID, UUID> displayEntities;

    public GameData(List<UUID> participants, @Nullable Location firstCorner, @Nullable Location secondCorner, Map<UUID, UUID> displayEntities) {
        this.participants = participants;
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.blocksInsideMap = Locations.getBlocksInsideCube(firstCorner, secondCorner);
        this.displayEntities = displayEntities;
    }

    public GameData() {
        this(new ArrayList<>(), null, null, new HashMap<>());
    }

    public void setFirstCorner(Location firstCorner) {
        this.firstCorner = firstCorner;
        this.blocksInsideMap = Locations.getBlocksInsideCube(firstCorner, this.secondCorner);
    }

    public void setSecondCorner(Location secondCorner) {
        this.secondCorner = secondCorner;
        this.blocksInsideMap = Locations.getBlocksInsideCube(this.firstCorner, secondCorner);
    }

}
