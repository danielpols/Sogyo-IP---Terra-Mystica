package terra.domain;

import java.util.List;

public class TerraMysticaFactory implements ITerraMysticaFactory {

    private static final int BOARD_SIZE = 13;

    public ITerraMystica startGame(List<String> names,
            List<Terrain> playerTerrains, Terrain[] board) {
        return new TerraMystica(new Player(names, playerTerrains), board,
                BOARD_SIZE);
    }

}
