package terra.api.models;

import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public class PlayerDTO {

    private String name;
    private Terrain terrain;
    private boolean turn;

    public PlayerDTO(ITerraMystica game, String name) {
        this.name = name;
        this.terrain = game.getPlayerTerrain(name);
        this.turn = game.playerHasTurn(name);
    }

    public String getName() {
        return this.name;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public boolean getTurn() {
        return this.turn;
    }

}
