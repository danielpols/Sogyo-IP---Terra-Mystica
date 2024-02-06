package terra.domain;

import java.util.List;
import java.util.stream.IntStream;

public class TerraMystica implements ITerraMystica {

    private final int boardSize;
    private Tile rootTile;
    private Player[] players;

    public TerraMystica(Player[] players, Terrain[] terrains, int boardSize) {
        this.boardSize = boardSize;
        List<Tile> tiles = IntStream.range(0, terrains.length)
                .mapToObj(i -> new Tile(
                        TileLocation.fromBoardIndex(i, this.boardSize),
                        terrains[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));
        this.rootTile = tiles.get(0);
        this.players = players;
    }

    public int[][] getTileLocations() {
        return rootTile.getTileLocations();
    }

    public Terrain getTileTerrain(int[] location) {
        return rootTile.getTileTerrain(TileLocation.fromArray(location));
    }

    public Player[] getPlayers() {
        return players;
    }

    protected List<Tile> getTiles() {
        return rootTile.getTileList();
    }

}
