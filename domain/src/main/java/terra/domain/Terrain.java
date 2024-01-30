package terra.domain;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

public enum Terrain {

    RIVER, PLAINS, SWAMP, LAKE, FOREST, MOUNTAINS, WASTELAND, DESERT;

    private static List<String> terrains = Arrays
            .stream(new String[] { "PLAINS", "SWAMP", "LAKE", "FOREST",
                    "MOUNTAINS", "WASTELAND", "DESERT" })
            .toList();

    protected boolean isBuildable() {
        return this.equals(RIVER) ? false : true;
    }

    private int getTerrainIndex() {
        return terrains.indexOf(this.name());
    }

    protected OptionalInt distanceTo(Terrain other) {
        if (!this.isBuildable() || !other.isBuildable()) {
            return OptionalInt.empty();
        }
        if (this.equals(other)) {
            return OptionalInt.of(0);
        }
        return OptionalInt
                .of(getNextTowards(other).distanceTo(other).orElseThrow() + 1);
    }

    protected Terrain getNextTowards(Terrain other) {
        if (!this.isBuildable() || !other.isBuildable()) {
            throw new RuntimeException(
                    "Cannot get next towards unbuildable terrain.");
        }
        if (this.equals(other)) {
            return this;
        }
        int size = terrains.size();
        int ind = this.getTerrainIndex();
        int oInd = other.getTerrainIndex();
        int newIndex = (ind - oInd > Math.floor(size / 2.0)
                || (oInd - ind < Math.ceil(size / 2.0) && oInd - ind >= 0))
                        ? ind + 1
                        : ind - 1;
        newIndex = newIndex % size;
        newIndex = newIndex < 0 ? newIndex + size : newIndex;
        return Terrain.valueOf(terrains.get(newIndex));
    }

    protected Terrain getNextTowards(Terrain other, int amount) {
        if (amount < 1) {
            return this;
        }
        if (amount == 1) {
            return getNextTowards(other);
        }
        return getNextTowards(other).getNextTowards(other, amount - 1);
    }

}
