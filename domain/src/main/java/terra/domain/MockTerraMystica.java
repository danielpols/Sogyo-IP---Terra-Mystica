package terra.domain;

import java.util.HashMap;
import java.util.stream.IntStream;

public class MockTerraMystica implements ITerraMystica {

    HashMap<MockLocation, Terrain> tiles;

    public MockTerraMystica() {
        tiles = new HashMap<MockLocation, Terrain>();
        tiles.put(new MockLocation(0, 0), Terrain.RIVER);
        tiles.put(new MockLocation(0, 1), Terrain.DESERT);
        tiles.put(new MockLocation(1, 0), Terrain.FOREST);
        tiles.put(new MockLocation(1, 1), Terrain.WASTELAND);

    }

    public MockTerraMystica(Terrain[] terrains, int rowLength) {
        tiles = new HashMap<MockLocation, Terrain>();
        IntStream.range(0, terrains.length).forEach(i -> {
            int doubleRow = i / (2 * rowLength - 1);
            int singleRow = (i - (2 * rowLength - 1) * doubleRow) / rowLength;
            int col = i - (2 * rowLength - 1) * doubleRow
                    - rowLength * singleRow;
            tiles.put(new MockLocation(2 * doubleRow + singleRow, col),
                    terrains[i]);
        });
    }

    public int[][] getTileLocations() {
        return tiles.keySet().stream().sorted((i, j) -> i.compare(j))
                .map(t -> t.toArray()).toArray(int[][]::new);
    }

    public Terrain getTileTerrain(int[] location) {
        return tiles.get(new MockLocation(location[0], location[1]));
    }

}

record MockLocation(int row, int col) {
    public int[] toArray() {
        return new int[] { row, col };
    }

    public int compare(MockLocation other) {
        return Integer.compare(row, other.row) == 0
                ? Integer.compare(col, other.col)
                : Integer.compare(row, other.row);
    }
}
