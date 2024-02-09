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

    public Building getTileBuilding(int[] location) {
        return rootTile.getTileBuilding(TileLocation.fromArray(location));
    }

    public boolean tileIsBuildable(int[] location) {
        return rootTile.isBuildable(TileLocation.fromArray(location),
                player.getTurnPlayer());
    }

    public Player[] getPlayers() {
        return player.getAllPlayers().toArray(Player[]::new);
    }

    public void passTurn() {
        player.getTurnPlayer().passTurn();
    }

    public void build(int[] location, Building building) {
        rootTile.build(TileLocation.fromArray(location), building,
                player.getTurnPlayer());
    }

    protected List<Tile> getTiles() {
        return rootTile.getTileList();
    }

}
