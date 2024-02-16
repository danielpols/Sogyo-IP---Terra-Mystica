package terra.persistence;

import java.util.List;

import terra.domain.ITerraMystica;
import terra.domain.ITerraMysticaFactory;
import terra.domain.Terrain;
import terra.domain.actions.GameAction;

public class TerraMysticaRepository implements ITerraMysticaRepository {

    ITerraMysticaDatabase database;
    ITerraMysticaFactory factory;

    public TerraMysticaRepository(ITerraMysticaDatabase database,
            ITerraMysticaFactory factory) {
        this.database = database;
        this.factory = factory;
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {
        if (!database.hasID(id)) {
            database.initialiseGame(id, playerNames, playerTerrains);
        }
    }

    public void saveAction(String id, GameAction action) {
        database.saveAction(id, action);
    }

    public ITerraMystica loadGame(String id) {
        ITerraMystica game = factory.startGame(database.getPlayerNames(id),
                database.getPlayerTerrains(id), getStartingTerrain());
        database.getGameActions(id).forEach(a -> {
            game.perform(a);
            game.endTurn(a.getPlayerName());
            game.startNewRoundIfAllPassed();
        });
        return game;
    }

    private Terrain[] getStartingTerrain() {
        return database.getStartingBoard().chars()
                .mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

}
