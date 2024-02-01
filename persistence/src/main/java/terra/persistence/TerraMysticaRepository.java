package terra.persistence;

import terra.domain.Terrain;

public class TerraMysticaRepository implements ITerraMysticaRepository {

    ITerraMysticaDatabase database;

    public TerraMysticaRepository(ITerraMysticaDatabase database) {
        this.database = database;
    }

    public Terrain[] getStartingTerrain() {
        return database.getStartingBoard().chars()
                .mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

}
