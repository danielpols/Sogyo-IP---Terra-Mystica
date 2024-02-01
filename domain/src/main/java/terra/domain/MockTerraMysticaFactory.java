package terra.domain;

public class MockTerraMysticaFactory implements ITerraMysticaFactory {

    private static final int BOARD_SIZE = 13;

    @Override
    public ITerraMystica startGame(String[] players, Terrain[] terrains) {
        if (terrains == null) {
            return new MockTerraMystica();
        }
        return new MockTerraMystica(terrains, BOARD_SIZE);
    }

}
