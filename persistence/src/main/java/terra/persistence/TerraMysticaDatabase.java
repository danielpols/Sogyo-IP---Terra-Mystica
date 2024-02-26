package terra.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import terra.domain.Terrain;
import terra.domain.actions.GameAction;
import terra.persistence.entities.PersistableGameObject;

public class TerraMysticaDatabase implements ITerraMysticaDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRPMP" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    EntityManager manager;

    public TerraMysticaDatabase() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("terramystica.odb");
        this.manager = emf.createEntityManager();
    }

    public Terrain[] getStartingBoard() {
        return start.chars().mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

    public boolean hasID(String id) {
        PersistableGameObject game = manager.find(PersistableGameObject.class,
                id);
        return game != null;
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {
        PersistableGameObject game = new PersistableGameObject(id, playerNames,
                playerTerrains);

        manager.getTransaction().begin();
        manager.persist(game);
        manager.getTransaction().commit();
    }

    public List<String> getPlayerNames(String id) {
        return manager.find(PersistableGameObject.class, id).getNameList();
    }

    public List<Terrain> getPlayerTerrains(String id) {
        return manager.find(PersistableGameObject.class, id).getTerrainList();
    }

    public List<GameAction> getGameActions(String id) {
        return manager.find(PersistableGameObject.class, id).getActionList()
                .stream().map(a -> a.toAction()).toList();
    }

    public void saveAction(String id, GameAction action) {
        PersistableGameObject game = manager.find(PersistableGameObject.class,
                id);
        manager.getTransaction().begin();
        game.addAction(action);
        manager.getTransaction().commit();
    }

}
