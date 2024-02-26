package terra.persistence;

import java.util.List;

import terra.domain.Terrain;
import terra.domain.actions.GameAction;

public interface ITerraMysticaDatabase {

    Terrain[] getStartingBoard();

    boolean hasID(String id);

    void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains);

    List<String> getPlayerNames(String id);

    List<Terrain> getPlayerTerrains(String id);

    List<GameAction> getGameActions(String id);

    void saveAction(String id, GameAction action);

}
