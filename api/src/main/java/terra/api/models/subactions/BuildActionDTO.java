package terra.api.models.subactions;

import terra.api.models.ActionDTO;
import terra.domain.Building;
import terra.domain.Terrain;
import terra.domain.actions.BuildAction;

public class BuildActionDTO extends ActionDTO {

    private int[] location;
    private Terrain playerTerrain;
    private Building targetBuilding;

    public BuildActionDTO() {

    }

    public BuildActionDTO(BuildAction action) {
        super(action);
        this.setLocation(action.getLocation());
        this.setPlayerTerrain(action.getPlayerTerrain());
        this.setTargetBuilding(action.getTargetBuilding());
    }

    public BuildAction toAction() {
        return new BuildAction(getPlayerName(), location, playerTerrain,
                targetBuilding);
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public Terrain getPlayerTerrain() {
        return playerTerrain;
    }

    public void setPlayerTerrain(Terrain playerTerrain) {
        this.playerTerrain = playerTerrain;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

}
