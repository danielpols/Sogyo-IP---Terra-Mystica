package terra.api.models;

import terra.domain.Player;
import terra.domain.Terrain;

public class PlayerDTO {

    private String name;
    private Terrain terrain;
    private boolean turn;

    public PlayerDTO(Player player) {
        this.name = player.getName();
        this.terrain = player.getTerrain();
        this.turn = player.hasTurn();
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
