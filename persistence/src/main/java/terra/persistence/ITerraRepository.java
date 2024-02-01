package terra.persistence;

import terra.domain.Terrain;

public interface ITerraRepository {

    Terrain[] getStartingTerrain();
}
