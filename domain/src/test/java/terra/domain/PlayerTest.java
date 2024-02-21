package terra.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terra.domain.actions.BuildAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.UpgradeAction;

public class PlayerTest {

    Player player;

    @BeforeEach
    public void testInit() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        player = new Player(names, terrains);
    }

    @Test
    public void testCanGetAllPlayers() {
        assertEquals(3, player.getPlayerList().size());
    }

    @Test
    public void testActivePlayerCanEndTurn() {
        assertTrue(player.findPlayer("Henk").hasTurn());
        player.endTurn("Henk");
        assertTrue(player.findPlayer("Jaap").hasTurn());
        assertFalse(player.findPlayer("Henk").hasTurn());
        player.endTurn("Piet");
        assertTrue(player.findPlayer("Jaap").hasTurn());
    }

    @Test
    public void testOnlyOneActivePlayer() {
        player.endTurn("Henk");
        assertEquals(1, player.getPlayerList().stream().filter(p -> p.hasTurn())
                .count());
    }

    @Test
    public void testEndTurnSkipsPassedPlayers() {
        player.perform(new PassAction("Henk", true));
        player.endTurn("Jaap");
        assertTrue(player.findPlayer("Piet").hasTurn());
        player.endTurn("Piet");
        assertTrue(player.findPlayer("Jaap").hasTurn());
    }

    @Test
    public void testNoTurnPlayerAfterAllPassed() {
        player.perform(new PassAction("Henk", true));
        player.perform(new PassAction("Jaap", false));
        player.perform(new PassAction("Piet", false));
        assertEquals(0, player.getPlayerList().stream().filter(p -> p.hasTurn())
                .count());
    }

    @Test
    public void testRepeatTurnIfAllOtherHavePassed() {
        player.perform(new PassAction("Henk", true));
        player.perform(new PassAction("Jaap", false));
        assertEquals("Piet", player.findTurnPlayer().getName());
        player.endTurn("Piet");
        assertEquals("Piet", player.findTurnPlayer().getName());
        player.endTurnReverse("Piet");
        assertEquals("Piet", player.findTurnPlayer().getName());
    }

    @Test
    public void testNonActivePlayerCannotEndTurn() {
        player.endTurn("Piet");
        player.endTurnReverse("Jaap");
        assertEquals("Henk", player.findTurnPlayer().getName());
    }

    @Test
    public void testEndOfRound() {
        player.endTurn("Henk");
        player.perform(new PassAction("Jaap", true));
        player.endTurn("Piet");
        player.perform(new PassAction("Henk", false));
        player.perform(new PassAction("Piet", false));

        assertEquals(0, player.getPlayerList().stream().filter(p -> p.hasTurn())
                .count());

        player.startNewRound();
        assertEquals(1, player.getPlayerList().stream().filter(p -> p.hasTurn())
                .count());
        assertEquals("Jaap", player.getPlayerList().stream()
                .filter(p -> p.hasTurn()).findFirst().get().getName());
    }

    @Test
    public void testReverseTurn() {
        player.endTurn("Henk");
        player.endTurnReverse("Jaap");
        assertTrue(player.findPlayer("Henk").hasTurn());
    }

    @Test
    public void testBuildingCosts() {
        assertEquals(new Resource(2, 1, 0),
                player.getBuildingCost("Henk", Building.DWELLING, false));
        assertEquals(new Resource(6, 2, 0),
                player.getBuildingCost("Henk", Building.TRADING, false));
        assertEquals(new Resource(3, 2, 0),
                player.getBuildingCost("Henk", Building.TRADING, true));
        assertEquals(new Resource(6, 4, 0),
                player.getBuildingCost("Henk", Building.FORTRESS, false));
        assertEquals(new Resource(5, 2, 0),
                player.getBuildingCost("Henk", Building.CHURCH, false));
        assertEquals(new Resource(6, 4, 0),
                player.getBuildingCost("Henk", Building.SANCTUARY, false));

        assertEquals(new Resource(0, 0, 0),
                player.getBuildingCost("Henk", Building.NONE, false));
    }

    @Test
    public void testImprovementCosts() {
        assertEquals(new Resource(4, 0, 1),
                player.findPlayer("Henk").getShippingImprovementCost());
        assertEquals(new Resource(5, 2, 1),
                player.findPlayer("Henk").getShovelImprovementCost());

        IntStream.range(0, 3).forEach(i -> player
                .perform(new ShippingAction("Henk", Resource.free(), 0)));

        IntStream.range(0, 2)
                .forEach(i -> player.perform(new ShovelAction("Henk",
                        Resource.free(), new Resource(0, 0, 0))));

        assertEquals(3, player.findPlayer("Henk").getShippingRange());
        assertArrayEquals((new Resource(0, 1, 0)).toArray(),
                player.findPlayer("Henk").getTerraformCost());
        assertNull(player.findPlayer("Henk").getShippingImprovementCost());
        assertNull(player.findPlayer("Henk").getShovelImprovementCost());
    }

    @Test
    public void testBuildingLimits() {
        player.perform(new BuildAction("Henk", Resource.free(), null,
                Terrain.DESERT, Building.DWELLING, 0));
        player.perform(new UpgradeAction("Henk", Resource.free(), null,
                Building.DWELLING, Building.TRADING));
        player.perform(new UpgradeAction("Henk", Resource.free(), null,
                Building.TRADING, Building.FORTRESS));

        assertFalse(player.canBuildBuilding(Building.FORTRESS));
    }

}
