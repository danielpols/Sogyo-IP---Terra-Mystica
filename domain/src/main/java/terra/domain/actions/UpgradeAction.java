package terra.domain.actions;

import terra.domain.Building;
import terra.domain.Resource;

public class UpgradeAction extends TileAction {

    private final Building sourceBuilding;

    public UpgradeAction(String playerName, Resource cost, int[] location,
            Building sourceBuilding, Building targetBuilding) {
        super(playerName, cost, location, targetBuilding);
        this.sourceBuilding = sourceBuilding;
    }

    public Building getSourceBuilding() {
        return sourceBuilding;
    }

}
