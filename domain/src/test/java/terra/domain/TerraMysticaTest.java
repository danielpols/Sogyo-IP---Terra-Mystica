package terra.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;

public class TerraMysticaTest {

    ITerraMystica defaultGame;

    @BeforeEach
    public void testInit() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.SWAMP, Terrain.SWAMP, Terrain.RIVER,
                Terrain.WASTELAND, Terrain.SWAMP };
        defaultGame = new TerraMysticaFactory().startGame(names, playerTerrains,
                terrains);
    }

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
        assertArrayEquals(names.toArray(String[]::new),
                defaultGame.getPlayerNames().toArray(String[]::new));
    }

    @Test
    public void testPassAction() {
        assertNull(defaultGame.getPassAction("Gerrit"));
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        assertNull(defaultGame.getPassAction("Gerrit"));

        assertInstanceOf(PassAction.class, defaultGame.getPassAction("Daniel"));

        PassAction action = (PassAction) defaultGame.getPassAction("Daniel");

        assertEquals("Daniel", action.getPlayerName());
        assertTrue(action.isStarting());

        defaultGame.perform(action);

        assertTrue(defaultGame.playerHasTurn("Gerrit"));
        assertTrue(defaultGame.playerHasPassed("Daniel"));
        assertTrue(defaultGame.isStartingPlayer("Daniel"));

        defaultGame.perform(action);

        assertTrue(defaultGame.playerHasTurn("Gerrit"));
    }

    @Test
    public void testTileBuildAction() {
        List<GameAction> actions = defaultGame.getTileActions("Daniel",
                new int[] { 0, 0 });

        assertEquals(1, actions.stream().filter(a -> a instanceof BuildAction)
                .toList().size());
        GameAction buildAction = actions.get(0);
        assertInstanceOf(BuildAction.class, buildAction);
        defaultGame.perform(buildAction);
        assertEquals(Building.DWELLING,
                defaultGame.getTileBuilding(new int[] { 0, 0 }));

        assertEquals(0,
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().filter(a -> a instanceof BuildAction).toList()
                        .size());

        defaultGame.perform(defaultGame
                .getTileActions("Daniel", new int[] { 0, 1 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());

        // across the river
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        List<GameAction> crossRiverActions = defaultGame
                .getTileActions("Daniel", new int[] { 0, 3 }).stream()
                .filter(a -> a instanceof BuildAction).toList();

        assertEquals(0,
                defaultGame.getTileActions("Daniel", new int[] { 0, 4 })
                        .stream().filter(a -> a instanceof BuildAction).toList()
                        .size());

        assertEquals(1, crossRiverActions.size());
        defaultGame.perform(crossRiverActions.get(0));
        assertEquals(Building.DWELLING,
                defaultGame.getTileBuilding(new int[] { 0, 3 }));
        assertEquals(defaultGame.getPlayerTerrain("Daniel"),
                defaultGame.getTileTerrain(new int[] { 0, 3 }));

    }

    @Test
    public void testGamePhaseChanges() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.SWAMP, Terrain.PLAINS, Terrain.MOUNTAINS,
                Terrain.DESERT, Terrain.SWAMP, Terrain.PLAINS,
                Terrain.MOUNTAINS, Terrain.DESERT };
        ITerraMystica game = new TerraMysticaFactory().startGame(names,
                playerTerrains, terrains);
        assertEquals(GamePhase.GAME_START, game.getGamePhase());
        assertEquals("Setup", game.getGamePhaseMessage());

        game.perform(game.getTileActions("Daniel", new int[] { 0, 0 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Daniel");

        game.perform(game.getTileActions("Gerrit", new int[] { 0, 1 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Gerrit");

        game.perform(game.getTileActions("Wesley", new int[] { 0, 2 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Wesley");

        game.perform(game.getTileActions("John", new int[] { 0, 3 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("John");

        assertEquals(GamePhase.GAME_START_REVERSE, game.getGamePhase());
        assertEquals("Setup", game.getGamePhaseMessage());

        game.perform(game.getTileActions("John", new int[] { 0, 7 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("John");

        game.perform(game.getTileActions("Wesley", new int[] { 0, 6 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Wesley");

        game.perform(game.getTileActions("Gerrit", new int[] { 0, 5 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Gerrit");

        game.perform(game.getTileActions("Daniel", new int[] { 0, 4 }).stream()
                .filter(a -> a instanceof BuildAction).findFirst().get());
        game.endTurn("Daniel");

        assertEquals(GamePhase.GAME_ROUND, game.getGamePhase());
    }

    @Test
    public void testRoundChanges() {
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        assertEquals("Round 1", defaultGame.getGamePhaseMessage());

        defaultGame.perform(defaultGame.getPassAction("Daniel"));
        defaultGame.startNewRoundIfAllPassed();
        assertEquals("Round 1", defaultGame.getGamePhaseMessage());

        defaultGame.perform(defaultGame.getPassAction("Gerrit"));
        defaultGame.perform(defaultGame.getPassAction("Wesley"));
        defaultGame.perform(defaultGame.getPassAction("John"));
        defaultGame.startNewRoundIfAllPassed();
        assertEquals("Round 2", defaultGame.getGamePhaseMessage());
        IntStream.range(0, 5).forEach(i -> {

            defaultGame.perform(defaultGame.getPassAction("Daniel"));
            defaultGame.perform(defaultGame.getPassAction("Gerrit"));
            defaultGame.perform(defaultGame.getPassAction("Wesley"));
            defaultGame.perform(defaultGame.getPassAction("John"));
            defaultGame.startNewRoundIfAllPassed();
        });
        assertEquals("Game has ended", defaultGame.getGamePhaseMessage());
    }

    @Test
    public void testBuildingUpgrades() {

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        assertEquals(Building.DWELLING,
                defaultGame.getTileBuilding(new int[] { 0, 0 }));
        assertEquals(1, defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                .size());

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        assertEquals(Building.TRADING,
                defaultGame.getTileBuilding(new int[] { 0, 0 }));
    }

    @Test
    public void testResourceCost() {

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);

        assertEquals(new Resource(15, 3, 0),
                defaultGame.getPlayerResource("Daniel"));
        assertEquals(new Resource(0, 2, 0),
                defaultGame.getPlayerIncome("Daniel"));

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        assertEquals(new Resource(9, 1, 0),
                defaultGame.getPlayerResource("Daniel"));
        assertEquals(new Resource(2, 1, 0),
                defaultGame.getPlayerIncome("Daniel"));
    }

}
