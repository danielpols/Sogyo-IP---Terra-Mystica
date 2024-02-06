package terra.domain;

public class MockTerraMysticaFactory implements ITerraMysticaFactory {

    private static final int BOARD_SIZE = 13;

    @Override
    public ITerraMystica startGame(Player[] players, Terrain[] terrains) {
        if (terrains == null) {
            return new MockTerraMystica();
        }
        if (players == null) {
            return new MockTerraMystica(terrains, BOARD_SIZE);
        }
        return new MockTerraMystica(players, terrains, BOARD_SIZE);
    }

}
