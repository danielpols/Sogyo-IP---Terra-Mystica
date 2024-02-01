package terra.persistence;

import terra.domain.Terrain;

public class TerraRepository implements ITerraRepository {

    ITerraDatabase database;

    public TerraRepository(ITerraDatabase database) {
        this.database = database;
    }

    public Terrain[] getStartingTerrain() {
        return database.getStartingBoard().chars()
                .mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

}
