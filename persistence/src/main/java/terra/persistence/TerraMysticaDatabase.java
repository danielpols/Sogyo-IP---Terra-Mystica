package terra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import terra.domain.Terrain;
import terra.domain.actions.GameAction;

public class TerraMysticaDatabase implements ITerraMysticaDatabase {

    EntityManagerFactory emf;
    EntityManager manager;

    public TerraMysticaDatabase() {
        this.emf = Persistence.createEntityManagerFactory("terramystica.odb");
        this.manager = emf.createEntityManager();
    }

    public String getStartingBoard() {
        return null;
    }

    public boolean hasID(String id) {
        return false;
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {

    }

    public List<String> getPlayerNames(String id) {
        return null;
    }

    public List<Terrain> getPlayerTerrains(String id) {
        return null;
    }

    public List<GameAction> getGameActions(String id) {
        return null;
    }

    public void saveAction(String id, GameAction action) {

    }

}
