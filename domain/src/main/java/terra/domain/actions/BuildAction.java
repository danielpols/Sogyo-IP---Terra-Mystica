package terra.domain.actions;

import terra.domain.Building;
import terra.domain.Terrain;

public class BuildAction extends TileAction {

    private final Building targetBuilding;

    public BuildAction(String playerName, int[] location, Terrain playerTerrain,
            Building targetBuilding) {
        super(playerName, location, playerTerrain);
        this.targetBuilding = targetBuilding;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

}
