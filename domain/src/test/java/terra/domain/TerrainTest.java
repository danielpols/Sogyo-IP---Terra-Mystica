package terra.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
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
    public void testDesertMovesToPlains() {
        assertEquals(Terrain.PLAINS,
                Terrain.DESERT.getNextTowards(Terrain.PLAINS));
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

    @Test
    public void testCanConvertTerrainToString() {
        assertAll(() -> {
            assertEquals("R", Terrain.RIVER.getTerrainString());
        }, () -> {
            assertEquals("P", Terrain.PLAINS.getTerrainString());
        }, () -> {
            assertEquals("S", Terrain.SWAMP.getTerrainString());
        }, () -> {
            assertEquals("L", Terrain.LAKE.getTerrainString());
        }, () -> {
            assertEquals("F", Terrain.FOREST.getTerrainString());
        }, () -> {
            assertEquals("M", Terrain.MOUNTAINS.getTerrainString());
        }, () -> {
            assertEquals("W", Terrain.WASTELAND.getTerrainString());
        }, () -> {
            assertEquals("D", Terrain.DESERT.getTerrainString());
        });
    }

    @Test
    public void testCanConvertCharToTerrain() {
        assertAll(() -> {
            assertEquals(Terrain.RIVER, Terrain.getTerrain('R'));
        }, () -> {
            assertEquals(Terrain.PLAINS, Terrain.getTerrain('P'));
        }, () -> {
            assertEquals(Terrain.SWAMP, Terrain.getTerrain('S'));
        }, () -> {
            assertEquals(Terrain.LAKE, Terrain.getTerrain('L'));
        }, () -> {
            assertEquals(Terrain.FOREST, Terrain.getTerrain('F'));
        }, () -> {
            assertEquals(Terrain.MOUNTAINS, Terrain.getTerrain('M'));
        }, () -> {
            assertEquals(Terrain.WASTELAND, Terrain.getTerrain('W'));
        }, () -> {
            assertEquals(Terrain.DESERT, Terrain.getTerrain('D'));
        });
    }

    @Test
    public void testNextTowardsSelfIsSelf() {
        Terrain source = Terrain.DESERT;
        assertEquals(source, source.getNextTowards(source));
    }

    @Test
    public void testZeroStepsTowardsOtherIsSelf() {
        Terrain source = Terrain.DESERT;
        Terrain target = Terrain.MOUNTAINS;
        assertEquals(source, source.getNextTowards(target, 0));
    }

}
