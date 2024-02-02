package terra.persistence;

import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public interface ITerraMysticaRepository {

    Terrain[] getStartingTerrain();

    void saveGame(String id, ITerraMystica game);

    ITerraMystica loadGame(String id);
}
