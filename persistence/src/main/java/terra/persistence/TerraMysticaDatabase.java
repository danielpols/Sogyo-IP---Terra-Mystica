package terra.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<String, PersistableGameObject> localDB;

    public TerraMysticaDatabase() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("../persistence/terramystica.odb");
        this.manager = emf.createEntityManager();
        this.localDB = new HashMap<String, PersistableGameObject>();
    }

    public Terrain[] getStartingBoard() {
        return start.chars().mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

    private PersistableGameObject findGame(String id) {
        if (!localDB.containsKey(id)) {
            PersistableGameObject game = manager
                    .find(PersistableGameObject.class, id);
            if (game != null) {
                localDB.put(id, game);
            }
        }
        return localDB.get(id);
    }

    public boolean hasID(String id) {
        return findGame(id) != null;
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {
        PersistableGameObject game = new PersistableGameObject(id, playerNames,
                playerTerrains);

        localDB.put(id, game);

        new Thread(() -> {
            manager.getTransaction().begin();
            manager.persist(game);
            manager.getTransaction().commit();
        }).start();
    }

    public List<String> getPlayerNames(String id) {
        return findGame(id).getNameList();
    }

    public List<Terrain> getPlayerTerrains(String id) {
        return findGame(id).getTerrainList();
    }

    public List<GameAction> getGameActions(String id) {
        return findGame(id).getActionList().stream().map(a -> a.toAction())
                .toList();
    }

    public void saveAction(String id, GameAction action) {
        manager.getTransaction().begin();
        findGame(id).addAction(action);
        new Thread(() -> {
            manager.getTransaction().commit();
        }).start();
    }

}
