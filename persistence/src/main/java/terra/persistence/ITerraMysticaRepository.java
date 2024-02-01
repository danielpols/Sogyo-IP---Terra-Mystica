package terra.persistence;

import terra.domain.Terrain;

public interface ITerraMysticaRepository {

    Terrain[] getStartingTerrain();
}
