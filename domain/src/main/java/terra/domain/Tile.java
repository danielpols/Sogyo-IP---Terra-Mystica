package terra.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import terra.domain.actions.BuildAction;
import terra.domain.actions.TileAction;
import terra.domain.actions.UpgradeAction;

public class Tile implements ITileInfo, ITileActionInfo {

    private final TileLocation location;
    private Terrain terrain;
    private List<Tile> adjacent;
    private Building building;

    protected Tile(TileLocation location, Terrain terrain) {
        this.location = location;
        this.terrain = terrain;
        this.building = Building.NONE;
    }

    protected void setAdjacent(List<Tile> others) {
        this.adjacent = others.stream().filter(t -> this.isAdjacentTo(t))
                .toList();
    }

    private boolean isAdjacentTo(Tile other) {
        return location.isAdjacentTo(other.location);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Building getBuilding() {
        return building;
    }

    public int[] getLocation() {
        return location.toArray();
    }

    protected List<Tile> getTileList() {
        return getAllTiles().stream().sorted((i, j) -> i.compare(j)).toList();
    }

    protected void perform(TileAction action) {
        if (action instanceof BuildAction) {
            findTile(TileLocation.fromArray(action.getLocation()))
                    .build((BuildAction) action);
        }
        if (action instanceof UpgradeAction) {
            findTile(TileLocation.fromArray(action.getLocation()))
                    .upgrade((UpgradeAction) action);
        }
    }

    private void build(BuildAction action) {
        if (location.equals(TileLocation.fromArray(action.getLocation()))) {
            if (building.upgradesTo(action.getTargetBuilding())) {
                building = action.getTargetBuilding();
                terrain = action.getPlayerTerrain();
            }
        }
    }

    private void upgrade(UpgradeAction action) {
        if (location.equals(TileLocation.fromArray(action.getLocation()))) {
            if (building.upgradesTo(action.getTargetBuilding())) {
                building = action.getTargetBuilding();
            }
        }
    }

    protected int getAmountOfBuildingsOn(Terrain playerTerrain) {
        return (int) getAllTiles().stream()
                .filter(t -> t.terrain.equals(playerTerrain) && t.hasBuilding())
                .count();
    }

    public boolean hasBuilding() {
        return !building.equals(Building.NONE);
    }

    public boolean isIndirectlyAdjacentTo(Terrain playerTerrain,
            int shippingRange) {
        return getAllIndirectAdjacentWithin(shippingRange + 1).stream()
                .filter(t -> t.terrain.equals(playerTerrain) && t.hasBuilding())
                .count() > 0;
    }

    private Set<Tile> getAllIndirectAdjacentWithin(int range) {
        HashSet<Tile> set = new HashSet<Tile>();
        getAllIndirectAdjacentWithin(range, set);
        return set;
    }

    private void getAllIndirectAdjacentWithin(int range, Set<Tile> set) {
        if (!set.contains(this)) {
            set.add(this);
        }
        if (range > 0) {
            set.addAll(adjacent);
            adjacent.stream().filter(t -> t.terrain.equals(Terrain.RIVER))
                    .forEach(t -> t.getAllIndirectAdjacentWithin(range - 1,
                            set));
        }
    }

    private int compare(Tile other) {
        return location.compare(other.location);
    }

    protected Tile findTile(TileLocation target) {
        return target.equals(location) ? this
                : adjacent.stream()
                        .filter(t -> t.location.distance(target) < location
                                .distance(target))
                        .findAny().get().findTile(target);
    }

    private HashSet<Tile> getAllTiles() {
        HashSet<Tile> tiles = new HashSet<Tile>();
        getAllTiles(tiles);
        return tiles;
    }

    private void getAllTiles(HashSet<Tile> set) {
        if (!set.contains(this)) {
            set.add(this);
            adjacent.forEach(t -> t.getAllTiles(set));
        }
    }

    public boolean hasAdjacencyBonus() {
        return adjacent.stream().filter(t -> t.hasBuilding())
                .filter(t -> !t.terrain.equals(terrain)).count() > 0;
    }

}

record TileLocation(int row, int col) {
    protected int[] toArray() {
        return new int[] { row(), col() };
    }

    protected static TileLocation fromArray(int[] array) {
        return new TileLocation(array[0], array[1]);
    }

    protected static TileLocation fromBoardIndex(int index, int size) {
        // Alternating rows of size and (size-1)
        int doubleRow = index / (2 * size - 1);
        int singleRow = (index - (2 * size - 1) * doubleRow) / size;
        int row = 2 * doubleRow + singleRow;
        int col = index - (2 * size - 1) * doubleRow - size * singleRow;
        return new TileLocation(row, col);
    }

    protected int compare(TileLocation other) {
        return Integer.compare(row, other.row) == 0
                ? Integer.compare(col, other.col)
                : Integer.compare(row, other.row);
    }

    protected boolean isAdjacentTo(TileLocation other) {
        if (other.row - row == 0 && Math.abs(other.col - col) == 1) {
            return true;
        }
        if (Math.abs(other.row - row) == 1 && (other.col == (col - 1 + row % 2)
                || other.col == (col + row % 2))) {
            return true;
        }

        return false;
    }

    protected int distance(TileLocation other) {
        int rowDiff = Math.abs(row - other.row);
        int halfColDiff = Math.abs(2 * col + (row % 2 == 0 ? 0 : 1)
                - (2 * other.col + (other.row % 2 == 0 ? 0 : 1)));
        if (halfColDiff <= rowDiff) {
            return rowDiff;
        }
        return (halfColDiff - rowDiff) / 2 + rowDiff;
    }
}
