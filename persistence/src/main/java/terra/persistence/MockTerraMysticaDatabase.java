package terra.persistence;

import java.util.HashMap;

import terra.domain.ITerraMystica;

public class MockTerraMysticaDatabase implements ITerraMysticaDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRPMP" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    private HashMap<String, ITerraMystica> database;

    public MockTerraMysticaDatabase() {
        database = new HashMap<String, ITerraMystica>();
    }

    public String getStartingBoard() {
        return start;
    }

    public void saveGame(String id, ITerraMystica game) {
        database.put(id, game);
    }

    public ITerraMystica loadGame(String id) {
        return database.get(id);
    }

}
