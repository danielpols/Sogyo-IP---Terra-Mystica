package terra.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TileLocationTest {

    @Test
    public void testTileLocationAdjacency() {
        TileLocation testEven = new TileLocation(2, 1);
        TileLocation[] adjacent = Arrays
                .stream(new int[][] { { 2, 0 }, { 2, 2 }, { 1, 0 }, { 1, 1 },
                        { 3, 0 }, { 3, 1 } })
                .map(i -> TileLocation.fromArray(i))
                .toArray(TileLocation[]::new);

        assertAll(Arrays.stream(adjacent).map(t -> (() -> {
            assertTrue(testEven.isAdjacentTo(t));
        })));
        assertFalse(testEven.isAdjacentTo(new TileLocation(3, 2)));

        TileLocation testOdd = new TileLocation(3, 3);
        TileLocation[] adjacentOdd = Arrays
                .stream(new int[][] { { 3, 2 }, { 3, 4 }, { 2, 3 }, { 2, 4 },
                        { 4, 3 }, { 4, 4 } })
                .map(i -> TileLocation.fromArray(i))
                .toArray(TileLocation[]::new);

        assertAll(Arrays.stream(adjacentOdd).map(t -> (() -> {
            assertTrue(testOdd.isAdjacentTo(t));
        })));
        assertFalse(testOdd.isAdjacentTo(new TileLocation(3, 3)));
    }

    @Test
    public void testHexDistance() {
        TileLocation target = new TileLocation(5, 2);
        TileLocation source1 = new TileLocation(2, 2);
        TileLocation source2 = new TileLocation(5, 8);
        TileLocation source3 = new TileLocation(6, 4);

        assertEquals(3, source1.distance(target));
        assertEquals(6, source2.distance(target));
        assertEquals(2, source3.distance(target));
        assertEquals(8, source1.distance(source2));
    }
}
