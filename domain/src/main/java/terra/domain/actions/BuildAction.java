package terra.domain.actions;

import terra.domain.Building;
import terra.domain.Terrain;

public class BuildAction extends TileAction {

    private final int terraformCost;
    private final Terrain playerTerrain;

    public BuildAction(String playerName, int[] location, Terrain playerTerrain,
            Building targetBuilding, int terraformCost) {
        super(playerName, location, targetBuilding);
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
