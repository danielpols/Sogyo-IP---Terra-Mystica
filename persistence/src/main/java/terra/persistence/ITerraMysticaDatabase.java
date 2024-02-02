package terra.persistence;

import terra.domain.ITerraMystica;

public interface ITerraMysticaDatabase {

    String getStartingBoard();

    void saveGame(String id, ITerraMystica game);

    ITerraMystica loadGame(String id);

}
