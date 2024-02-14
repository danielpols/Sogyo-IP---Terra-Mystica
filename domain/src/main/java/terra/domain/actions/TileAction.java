package terra.domain.actions;

import terra.domain.Terrain;

public class TileAction extends GameAction {

    private final int[] location;
    private final Terrain playerTerrain;

    protected TileAction(String playerName, int[] location,
            Terrain playerTerrain) {
        super(playerName);
        this.location = location;
        this.playerTerrain = playerTerrain;
    }

    public int[] getLocation() {
        return location;
    }

    public Terrain getPlayerTerrain() {
        return playerTerrain;
    }

}
