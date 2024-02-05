package terra.api.models;

import terra.domain.Player;
import terra.domain.Terrain;

public class StartPlayerDTO {

    private String name;
    private Terrain terrain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Player toPlayer() {
        return new Player(name, terrain);
    }

}
