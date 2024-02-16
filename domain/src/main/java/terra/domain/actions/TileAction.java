package terra.domain.actions;

import terra.domain.Building;
import terra.domain.Resource;

public abstract class TileAction extends GameAction {

    private final int[] location;
    private final Building targetBuilding;

    protected TileAction(String playerName, Resource cost, int[] location,
            Building targetBuilding) {
        super(playerName, cost);
        this.location = location;
        this.targetBuilding = targetBuilding;
    }

    public int[] getLocation() {
        return location;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

}
