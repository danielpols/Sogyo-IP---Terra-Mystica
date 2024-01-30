package terra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TerrainTest {

    @Test
    public void testInitial() {
        assertEquals(8, Terrain.values().length);
    }

    public static Stream<Terrain> getAllTerrains() {
        return Stream.of(Terrain.values());
    }

    @ParameterizedTest
    @MethodSource("getAllTerrains")
    public void testRiverIsNotBuildable(Terrain t) {
        boolean expected = t.equals(Terrain.RIVER) ? false : true;
        assertEquals(expected, t.isBuildable());
    }

    @Test
    public void testPlainsAndDesertCloseTogether() {
        assertEquals(1,
                Terrain.PLAINS.distanceTo(Terrain.DESERT).orElseThrow());
    }

    @Test
    public void testWastelandAndLakeFarApart() {
        assertEquals(3,
                Terrain.LAKE.distanceTo(Terrain.WASTELAND).orElseThrow());
    }

    @Test
    public void testLakeMovesCloserToWasteland() {
        assertEquals(Terrain.FOREST,
                Terrain.LAKE.getNextTowards(Terrain.WASTELAND));
    }

    @Test
    public void testWastelandMovesCloserToLake() {
        assertEquals(Terrain.MOUNTAINS,
                Terrain.WASTELAND.getNextTowards(Terrain.LAKE));
    }

    @Test
    public void testCanMoveTwoStepsTowardsTile() {
        assertEquals(Terrain.MOUNTAINS,
                Terrain.DESERT.getNextTowards(Terrain.FOREST, 2));
    }

    @ParameterizedTest
    @MethodSource("getAllTerrains")
    public void testNoDistanceToRiver(Terrain t) {
        assertTrue(t.distanceTo(Terrain.RIVER).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getAllTerrains")
    public void testNoNextTerrainToRiver(Terrain t) {
        assertThrows(RuntimeException.class, () -> {
            t.getNextTowards(Terrain.RIVER);
        });
    }

}
