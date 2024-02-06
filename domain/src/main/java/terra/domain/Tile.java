package terra.domain;

public class Tile {

    private final TileLocation location;
    private Terrain terrain;

    public Tile(TileLocation location, Terrain terrain) {
        this.location = location;
        this.terrain = terrain;
    }

    public TileLocation getLocation() {
        return location;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}

record TileLocation(int row, int col) {
    public int[] toArray() {
        return new int[] { row, col };
    }

    public static TileLocation fromArray(int[] array) {
        return new TileLocation(array[0], array[1]);
    }

    public static TileLocation fromBoardIndex(int index, int size) {
        // Alternating rows of size and (size-1)
        int doubleRow = index / (2 * size - 1);
        int singleRow = (index - (2 * size - 1) * doubleRow) / size;
        int row = 2 * doubleRow + singleRow;
        int col = index - (2 * size - 1) * doubleRow - size * singleRow;
        return new TileLocation(row, col);
    }

    public int compare(TileLocation other) {
        return Integer.compare(row, other.row) == 0
                ? Integer.compare(col, other.col)
                : Integer.compare(row, other.row);
    }
}
