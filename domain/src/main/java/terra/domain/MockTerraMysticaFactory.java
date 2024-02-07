package terra.domain;

import java.util.List;

public class MockTerraMysticaFactory implements ITerraMysticaFactory {

    private static final int BOARD_SIZE = 13;

    @Override
    public ITerraMystica startGame(List<String> names,
            List<Terrain> playerTerrains, Terrain[] board) {
        if (board == null) {
            return new MockTerraMystica();
        }
        if (names == null) {
            return new MockTerraMystica(board, BOARD_SIZE);
        }
        return new MockTerraMystica(new Player(names, playerTerrains), board,
                BOARD_SIZE);
    }

}
