package terra.domain;

import java.util.List;
import java.util.stream.IntStream;

public class TerraMysticaFactory implements ITerraMysticaFactory {

    private static final int BOARD_SIZE = 13;

    public ITerraMystica startGame(List<String> names,
            List<Terrain> playerTerrains, Terrain[] board) {

        List<Tile> tiles = IntStream.range(0, board.length)
                .mapToObj(i -> new Tile(
                        TileLocation.fromBoardIndex(i, BOARD_SIZE), board[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));

        Tile rootTile = tiles.get(0);
        Player player = new Player(names, playerTerrains);

        return new TerraMystica(player, rootTile);
    }

}
