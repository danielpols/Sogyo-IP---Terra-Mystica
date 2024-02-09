package terra.api.models;

import terra.domain.Building;
import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public class TileDTO {

    private int[] location;
    private Terrain terrain;
    private Building building;
    private boolean buildable;

    public TileDTO(ITerraMystica game, int[] location) {
        this.location = new int[] { location[0], location[1] };
        this.terrain = game.getTileTerrain(location);
        this.building = game.getTileBuilding(location);
        this.buildable = game.tileIsBuildable(location);
    }

    public int[] getLocation() {
        return location;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Building getBuilding() {
        return building;
    }

    public boolean getBuildable() {
        return buildable;
    }

}
