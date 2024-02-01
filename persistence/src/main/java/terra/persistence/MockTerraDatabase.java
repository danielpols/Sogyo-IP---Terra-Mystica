package terra.persistence;

public class MockTerraDatabase implements ITerraDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRFMF" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    public String getStartingBoard() {
        return start;
    }

}
