package terra.api.models;

import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public class TileDTO {

    private int[] location;
    private Terrain terrain;

    public TileDTO(ITerraMystica game, int[] location) {
        this.location = new int[] { location[0], location[1] };
        this.terrain = game.getTileTerrain(location);
    }

    public int[] getLocation() {
        return location;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
