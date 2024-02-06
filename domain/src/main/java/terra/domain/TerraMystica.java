package terra.domain;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TerraMystica implements ITerraMystica {

    private final int boardSize;
    private Tile[] tiles;
    private Player[] players;

    public TerraMystica(Player[] players, Terrain[] terrains, int boardSize) {
        this.boardSize = boardSize;
        this.tiles = IntStream.range(0, terrains.length)
                .mapToObj(i -> new Tile(
                        TileLocation.fromBoardIndex(i, this.boardSize),
                        terrains[i]))
                .toArray(Tile[]::new);
        Arrays.stream(this.tiles)
                .forEach(t -> t.setAdjacent(Arrays.asList(tiles)));
        this.players = players;
    }

    public int[][] getTileLocations() {
        return Arrays.stream(tiles).map(t -> t.getLocation().toArray())
                .toArray(int[][]::new);
    }

    public Terrain getTileTerrain(int[] location) {
        return Arrays.stream(tiles).filter(
                t -> t.getLocation().equals(TileLocation.fromArray(location)))
                .map(t -> t.getTerrain()).toArray(Terrain[]::new)[0];
    }

    public Player[] getPlayers() {
        return players;
    }

    protected Tile[] getTiles() {
        return tiles;
    }

}
