package terra.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
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
    public void testCanGetPlayers() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        ITerraMystica game = new TerraMysticaFactory().startGame(names,
                playerTerrains, terrains);
        assertArrayEquals(names.toArray(String[]::new),
                Arrays.stream(game.getPlayers()).map(p -> p.getName())
                        .toArray(String[]::new));
    }

    @Test
    public void testPassTurn() {

        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        ITerraMystica game = new TerraMysticaFactory().startGame(names,
                playerTerrains, terrains);

        game.passTurn();
        assertEquals(names.get(1), Arrays.stream(game.getPlayers())
                .filter(p -> p.hasTurn()).findAny().get().getName());
    }

    @Test
    public void testBuildAction() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };
        ITerraMystica game = new TerraMystica(null, terrains, 2);

        game.build(new int[] { 1, 0 }, Building.DWELLING);
        assertEquals(Building.DWELLING,
                game.getTileBuilding(new int[] { 1, 0 }));
    }

}
