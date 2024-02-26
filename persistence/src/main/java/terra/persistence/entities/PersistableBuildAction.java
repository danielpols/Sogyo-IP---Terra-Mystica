package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.Building;
import terra.domain.Terrain;
import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;

@Embeddable
public class PersistableBuildAction extends PersistableGameAction {

    int[] location;
    Terrain playerTerrain;
    Building targetBuilding;
    public int terraformCost;

    public PersistableBuildAction(BuildAction action) {
        super(action);
        this.location = action.getLocation();
        this.playerTerrain = action.getPlayerTerrain();
        this.targetBuilding = action.getTargetBuilding();
        this.terraformCost = action.getTerraformCost();
    }

    public GameAction toAction() {
        return new BuildAction(playerName, getResourceCost(), location,
                playerTerrain, targetBuilding, terraformCost);
    }

}