package terra.persistence;

public class MockTerraMysticaDatabase implements ITerraMysticaDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRPMP" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    public String getStartingBoard() {
        return start;
    }

}
