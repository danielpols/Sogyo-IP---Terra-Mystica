package terra.domain;

import java.util.List;
import java.util.stream.IntStream;

public class TerraMystica implements ITerraMystica {

    private final int boardSize;
    private Tile rootTile;
    private Player player;

    public TerraMystica(Player player, Terrain[] board, int boardSize) {
        this.boardSize = boardSize;
        List<Tile> tiles = IntStream.range(0, board.length)
                .mapToObj(i -> new Tile(
                        TileLocation.fromBoardIndex(i, this.boardSize),
                        board[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));
        this.rootTile = tiles.get(0);
        this.player = player;
    }

    public int[][] getTileLocations() {
        return rootTile.getTileLocations();
    }

    public Terrain getTileTerrain(int[] location) {
        return rootTile.getTileTerrain(TileLocation.fromArray(location));
    }

    public Player[] getPlayers() {
        return player.getAllPlayers().toArray(Player[]::new);
    }

    protected List<Tile> getTiles() {
        return rootTile.getTileList();
    }

}
