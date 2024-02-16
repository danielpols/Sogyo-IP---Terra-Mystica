package terra.domain.actions;

import terra.domain.Building;

public class TileAction extends GameAction {

    private final int[] location;
    private final Building targetBuilding;

    protected TileAction(String playerName, int[] location,
            Building targetBuilding) {
        super(playerName);
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
