package terra.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import terra.domain.actions.PassAction;

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
                game.getPlayerNames().toArray(String[]::new));
    }

    @Test
    public void testPassAction() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.SWAMP, Terrain.SWAMP, Terrain.RIVER,
                Terrain.WASTELAND, Terrain.SWAMP };
        ITerraMystica game = new TerraMysticaFactory().startGame(names,
                playerTerrains, terrains);

        assertNull(game.getPassAction("Gerrit"));

        assertInstanceOf(PassAction.class, game.getPassAction("Daniel"));

        PassAction action = (PassAction) game.getPassAction("Daniel");

        assertEquals("Daniel", action.getPlayerName());
        assertTrue(action.isStarting());

        game.perform(action);

        assertTrue(game.playerHasTurn("Gerrit"));
        assertTrue(game.playerHasPassed("Daniel"));
        assertTrue(game.isStartingPlayer("Daniel"));

        game.perform(action);

        assertTrue(game.playerHasTurn("Gerrit"));
    }

    @Test
    public void testTileBuildAction() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.SWAMP, Terrain.SWAMP, Terrain.RIVER,
                Terrain.WASTELAND, Terrain.SWAMP };
        ITerraMystica game = new TerraMysticaFactory().startGame(names,
                playerTerrains, terrains);
    }

}
