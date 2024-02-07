package terra.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MockTerraMysticaTest {

    static ITerraMystica mock;

    @BeforeAll
    public static void intialise() {
        ITerraMysticaFactory factory = new MockTerraMysticaFactory();
        mock = factory.startGame(null, null, null);
    }

    @Test
    public void testGetLocations() {
        int[][] expected = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
        assertAll(IntStream.range(0, expected.length)
                .mapToObj(i -> (() -> assertArrayEquals(expected[i],
                        mock.getTileLocations()[i]))));
    }

    @Test
    public void testGetTerrains() {
        Terrain[] expected = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND };
        assertArrayEquals(expected, Arrays.stream(mock.getTileLocations())
                .map(loc -> mock.getTileTerrain(loc)).toArray(Terrain[]::new));
    }

    @Test
    public void testMockWithTerrainInput() {
        Terrain[] terrains = { Terrain.DESERT, Terrain.RIVER, Terrain.MOUNTAINS,
                Terrain.WASTELAND, Terrain.SWAMP };
        ITerraMystica game = new MockTerraMystica(terrains, 2);

        assertAll(() -> {
            assertEquals(Terrain.DESERT,
                    game.getTileTerrain(new int[] { 0, 0 }));
        }, () -> {
            assertEquals(Terrain.RIVER,
                    game.getTileTerrain(new int[] { 0, 1 }));
        }, () -> {
            assertEquals(Terrain.MOUNTAINS,
                    game.getTileTerrain(new int[] { 1, 0 }));
        }, () -> {
            assertEquals(Terrain.WASTELAND,
                    game.getTileTerrain(new int[] { 2, 0 }));
        }, () -> {
            assertEquals(Terrain.SWAMP,
                    game.getTileTerrain(new int[] { 2, 1 }));
        });

    }

}
