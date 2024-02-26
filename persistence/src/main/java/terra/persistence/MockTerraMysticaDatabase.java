package terra.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import terra.domain.Terrain;
import terra.domain.actions.GameAction;

public class MockTerraMysticaDatabase implements ITerraMysticaDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRPMP" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    private HashMap<String, List<String>> names;
    private HashMap<String, List<Terrain>> terrains;
    private HashMap<String, List<GameAction>> actions;

    public MockTerraMysticaDatabase() {
        names = new HashMap<String, List<String>>();
        terrains = new HashMap<String, List<Terrain>>();
        actions = new HashMap<String, List<GameAction>>();
    }

    public Terrain[] getStartingBoard() {
        return start.chars().mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

    public boolean hasID(String id) {
        return names.keySet().contains(id) && terrains.keySet().contains(id)
                && actions.keySet().contains(id);
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {
        names.put(id, playerNames);
        terrains.put(id, playerTerrains);
        actions.put(id, new ArrayList<GameAction>());
    }

    public List<String> getPlayerNames(String id) {
        return names.get(id);
    }

    public List<Terrain> getPlayerTerrains(String id) {
        return terrains.get(id);
    }

    public List<GameAction> getGameActions(String id) {
        return actions.get(id);
    }

    public void saveAction(String id, GameAction action) {
        actions.get(id).add(action);
    }

}
