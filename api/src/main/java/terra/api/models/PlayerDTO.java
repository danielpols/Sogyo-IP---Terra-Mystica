package terra.api.models;

import terra.domain.Player;
import terra.domain.Terrain;

public class PlayerDTO {

    private String name;
    private Terrain terrain;

    public PlayerDTO(Player player) {
        this.name = player.getName();
        this.terrain = player.getTerrain();
    }

    public String getName() {
        return this.name;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

}
