package terra.domain.actions;

import terra.domain.Building;

public class UpgradeAction extends TileAction {

    private final Building sourceBuilding;

    public UpgradeAction(String playerName, int[] location,
            Building sourceBuilding, Building targetBuilding) {
        super(playerName, location, targetBuilding);
        this.sourceBuilding = sourceBuilding;
    }

    public Building getSourceBuilding() {
        return sourceBuilding;
    }

}
