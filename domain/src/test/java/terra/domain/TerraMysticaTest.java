package terra.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class TerraMysticaTest {

    @Test
    public void testAllLocationsPresent() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        ITerraMystica game = new TerraMystica(null, terrains, 2);
        int[][] expected = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 }, { 2, 1 } };
        assertArrayEquals(
                Arrays.stream(expected).map(i -> TileLocation.fromArray(i))
                        .toArray(TileLocation[]::new),
                Arrays.stream(game.getTileLocations())
                        .map(i -> TileLocation.fromArray(i))
                        .toArray(TileLocation[]::new));
    }

    @Test
    public void testCanGetTerrains() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        ITerraMystica game = new TerraMystica(null, terrains, 2);
        int[][] locations = game.getTileLocations();
        assertAll(IntStream.range(0, terrains.length).mapToObj(i -> (() -> {
            assertEquals(terrains[i], game.getTileTerrain(locations[i]));
        })));
    }

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
    public void testAssureFourCorners() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        TerraMystica game = new TerraMystica(null, terrains, 2);
        assertEquals(4, Arrays.stream(game.getTiles())
                .filter(t -> t.getAdjacent().size() == 2).count());
    }

}
