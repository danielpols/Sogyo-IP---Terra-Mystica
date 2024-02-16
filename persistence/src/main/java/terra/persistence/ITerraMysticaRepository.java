package terra.persistence;

import java.util.List;

import terra.domain.ITerraMystica;
import terra.domain.Terrain;
import terra.domain.actions.GameAction;

public interface ITerraMysticaRepository {

    void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains);

    void saveAction(String id, GameAction action);

    ITerraMystica loadGame(String id);
}
