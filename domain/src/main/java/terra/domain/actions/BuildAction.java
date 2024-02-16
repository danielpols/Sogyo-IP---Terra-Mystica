package terra.domain.actions;

import terra.domain.Building;
import terra.domain.Resource;
import terra.domain.Terrain;

public class BuildAction extends TileAction {

    private final int terraformCost;
    private final Terrain playerTerrain;

    public BuildAction(String playerName, Resource cost, int[] location,
            Terrain playerTerrain, Building targetBuilding, int terraformCost) {
        super(playerName, cost, location, targetBuilding);
        this.playerTerrain = playerTerrain;
        this.terraformCost = terraformCost;
    }

    public int getTerraformCost() {
        return terraformCost;
    }

    public Terrain getPlayerTerrain() {
        return playerTerrain;
    }

}
