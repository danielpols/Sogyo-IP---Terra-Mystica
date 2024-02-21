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
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.UpgradeAction;

public class TerraMysticaTest {

    ITerraMystica defaultGame;

    @BeforeEach
    public void testInit() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        List<Terrain> playerTerrains = Arrays.asList(Terrain.SWAMP,
                Terrain.PLAINS, Terrain.MOUNTAINS, Terrain.DESERT);
        Terrain[] terrains = { Terrain.SWAMP, Terrain.SWAMP, Terrain.RIVER,
                Terrain.SWAMP, Terrain.SWAMP, Terrain.PLAINS };
        defaultGame = new TerraMysticaFactory().startGame(names, playerTerrains,
                terrains);
    }

    @Test
    public void testAllLocationsPresent() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };

        List<Tile> tiles = IntStream.range(0, terrains.length).mapToObj(
                i -> new Tile(TileLocation.fromBoardIndex(i, 2), terrains[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));

        ITerraMystica game = new TerraMystica(null, tiles.get(0));
        int[][] expected = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 2, 0 }, { 2, 1 } };
        assertArrayEquals(
                Arrays.stream(expected).map(i -> TileLocation.fromArray(i))
                        .toArray(TileLocation[]::new),
                game.getTileInfo().stream()
                        .map(t -> TileLocation.fromArray(t.getLocation()))
                        .toArray(TileLocation[]::new));
    }

    @Test
    public void testCanGetTerrains() {
        Terrain[] terrains = { Terrain.RIVER, Terrain.DESERT, Terrain.FOREST,
                Terrain.WASTELAND, Terrain.LAKE };

        List<Tile> tiles = IntStream.range(0, terrains.length).mapToObj(
                i -> new Tile(TileLocation.fromBoardIndex(i, 2), terrains[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));

        ITerraMystica game = new TerraMystica(null, tiles.get(0));
        int[][] locations = game.getTileInfo().stream()
                .map(t -> t.getLocation()).toArray(int[][]::new);
        assertAll(IntStream.range(0, terrains.length).mapToObj(i -> (() -> {
            assertEquals(terrains[i], game.getTile(locations[i]).getTerrain());
        })));
    }

    @Test
    public void testCanGetPlayers() {
        List<String> names = Arrays.asList("Daniel", "Gerrit", "Wesley",
                "John");
        assertArrayEquals(names.toArray(String[]::new),
                defaultGame.getPlayerInfo().stream().map(p -> p.getName())
                        .toArray(String[]::new));
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

        assertTrue(defaultGame.getPlayer("Gerrit").hasTurn());
        assertTrue(defaultGame.getPlayer("Daniel").hasPassed());
        assertTrue(defaultGame.getPlayer("Daniel").isNewStartingPlayer());

        defaultGame.perform(action);

        assertTrue(defaultGame.getPlayer("Gerrit").hasTurn());
    }

    @Test
    public void testTileBuildAction() {
        List<GameAction> actions = defaultGame.getTileActions("Daniel",
                new int[] { 0, 1 });

        assertEquals(1, actions.stream().filter(a -> a instanceof BuildAction)
                .toList().size());
        GameAction buildAction = actions.get(0);
        assertInstanceOf(BuildAction.class, buildAction);
        defaultGame.perform(buildAction);
        assertEquals(Building.DWELLING,
                defaultGame.getTile(new int[] { 0, 1 }).getBuilding());

        assertEquals(0,
                defaultGame.getTileActions("Daniel", new int[] { 0, 1 })
                        .stream().filter(a -> a instanceof BuildAction).toList()
                        .size());

        // across the river
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);

        defaultGame.perform(new ShippingAction("Daniel", Resource.free(), 1));

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
                defaultGame.getTile(new int[] { 0, 3 }).getBuilding());
        assertEquals(defaultGame.getPlayer("Daniel").getTerrain(),
                defaultGame.getTile(new int[] { 0, 3 }).getTerrain());

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
        assertArrayEquals(new int[] { 15, 6, 0 },
                game.getPlayer("Daniel").getResources());
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
                defaultGame.getTile(new int[] { 0, 0 }).getBuilding());
        assertEquals(1, defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                .size());

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        assertEquals(Building.TRADING,
                defaultGame.getTile(new int[] { 0, 0 }).getBuilding());
    }

    @Test
    public void testResourceCost() {

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);

        assertArrayEquals((new Resource(15, 3, 0)).toArray(),
                defaultGame.getPlayer("Daniel").getResources());
        assertArrayEquals((new Resource(0, 2, 0)).toArray(),
                defaultGame.getPlayer("Daniel").getIncome());

        defaultGame.perform(
                defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                        .stream().findFirst().get());

        assertArrayEquals((new Resource(9, 1, 0)).toArray(),
                defaultGame.getPlayer("Daniel").getResources());
        assertArrayEquals((new Resource(2, 1, 0)).toArray(),
                defaultGame.getPlayer("Daniel").getIncome());
    }

    @Test
    public void testImprovementActions() {
        assertNull(defaultGame.getShippingAction("Daniel"));
        assertNull(defaultGame.getShovelAction("Daniel"));

        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        assertNull(defaultGame.getShippingAction("Gerrit"));
        assertNull(defaultGame.getShovelAction("Gerrit"));
        assertNull(defaultGame.getShippingAction("Daniel"));
        assertNull(defaultGame.getShovelAction("Daniel"));

        // give priest
        defaultGame.perform(new BuildAction("Daniel", Resource.free(),
                new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));
        defaultGame.perform(new UpgradeAction("Daniel", Resource.free(),
                new int[] { 0, 0 }, Building.DWELLING, Building.TRADING));
        defaultGame.perform(new UpgradeAction("Daniel", Resource.free(),
                new int[] { 0, 0 }, Building.TRADING, Building.CHURCH));

        defaultGame.perform(new PassAction("Daniel", true));
        defaultGame.perform(new PassAction("Gerrit", false));
        defaultGame.perform(new PassAction("Wesley", false));
        defaultGame.perform(new PassAction("John", false));

        defaultGame.startNewRoundIfAllPassed();

        assertNotNull(defaultGame.getShippingAction("Daniel"));
        assertNotNull(defaultGame.getShovelAction("Daniel"));
    }

    @Test
    public void testInvalidBuildActions() {
        defaultGame.perform(new BuildAction("Daniel", new Resource(15, 3, 0),
                new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));

        IntStream.range(0, 6).forEach(i -> {

            defaultGame.perform(new BuildAction("Daniel", Resource.free(),
                    new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));
        });

        // cannot build on river
        assertEquals(0, defaultGame.getTileActions("Daniel", new int[] { 0, 2 })
                .size());

        // cannot build on other terrains during setup
        assertEquals(0, defaultGame.getTileActions("Daniel", new int[] { 0, 5 })
                .size());

        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);

        // cannot build with no resources
        assertArrayEquals(new int[] { 0, 0, 0 },
                defaultGame.getPlayer("Daniel").getResources());
        assertEquals(0, defaultGame.getTileActions("Daniel", new int[] { 0, 1 })
                .size());

        defaultGame.perform(new BuildAction("Daniel", new Resource(-5, -5, -5),
                new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));

        // cannot build due to building limit
        assertEquals(0, defaultGame.getTileActions("Daniel", new int[] { 0, 1 })
                .size());
    }

    @Test
    public void testInvalidUpgradeActions() {

        TerraMystica game = (TerraMystica) defaultGame;

        game.setGamePhase(GamePhase.GAME_ROUND);

        // cannot upgrade empty tile
        assertEquals(0,
                game.getTileActions("Daniel", new int[] { 0, 0 }).size());

        game.perform(new BuildAction("Daniel", Resource.free(),
                new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));
        game.perform(new PassAction("Daniel", true));

        // cannot upgrade non-owned tile
        assertEquals(0,
                game.getTileActions("Gerrit", new int[] { 0, 0 }).size());

        game.perform(new BuildAction("Gerrit", Resource.free(),
                new int[] { 0, 1 }, Terrain.PLAINS, Building.DWELLING, 0));
        game.perform(new BuildAction("Gerrit", Resource.free(),
                new int[] { 0, 2 }, Terrain.PLAINS, Building.DWELLING, 0));

        assertEquals(1,
                game.getTileActions("Gerrit", new int[] { 0, 1 }).size());
        assertEquals(1,
                game.getTileActions("Gerrit", new int[] { 0, 2 }).size());

        IntStream.range(0, 3).forEach(i -> {
            game.perform(new UpgradeAction("Gerrit", new Resource(4, 1, 0),
                    new int[] { 0, 1 }, Building.DWELLING, Building.TRADING));
        });

        // no resources
        assertEquals(0,
                game.getTileActions("Gerrit", new int[] { 0, 2 }).size());

        game.perform(new UpgradeAction("Gerrit", new Resource(-6, -6, 0),
                new int[] { 0, 1 }, Building.DWELLING, Building.TRADING));

        // no buildings left
        assertEquals(0,
                game.getTileActions("Gerrit", new int[] { 0, 2 }).size());
    }

    @Test
    public void testActionGetters() {
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_ROUND);
        defaultGame.perform(new BuildAction("Daniel", new Resource(0, -2, -1),
                new int[] { 0, 0 }, Terrain.SWAMP, Building.DWELLING, 0));

        assertEquals(1,
                ((ShippingAction) defaultGame.getShippingAction("Daniel"))
                        .getNewRange());

        // this is placeholder
        assertEquals(Resource.free(),
                ((ShovelAction) defaultGame.getShovelAction("Daniel"))
                        .getNewCost());

        assertEquals(0,
                ((BuildAction) defaultGame
                        .getTileActions("Daniel", new int[] { 0, 1 }).get(0))
                        .getTerraformCost());
    }

    @Test
    public void testNoActionsAtGameEnd() {
        ((TerraMystica) defaultGame).setGamePhase(GamePhase.GAME_END);

        assertEquals(0, defaultGame.getTileActions("Daniel", new int[] { 0, 0 })
                .size());
    }

}
