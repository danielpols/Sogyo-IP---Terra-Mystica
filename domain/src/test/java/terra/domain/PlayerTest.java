package terra.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;

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
        assertEquals(3, player.getAllPlayers().size());
    }

    @Test
    public void testActivePlayerCanEndTurn() {
        assertTrue(player.getPlayer("Henk").hasTurn());
        player.endTurn("Henk");
        assertTrue(player.getPlayer("Jaap").hasTurn());
        assertFalse(player.getPlayer("Henk").hasTurn());
        player.endTurn("Piet");
        assertTrue(player.getPlayer("Jaap").hasTurn());
    }

    @Test
    public void testOnlyOneActivePlayer() {
        player.endTurn("Henk");
        assertEquals(1, player.getAllPlayers().stream().filter(p -> p.hasTurn())
                .count());
    }

    @Test
    public void testEndTurnSkipsPassedPlayers() {
        player.perform(new PassAction("Henk", true));
        player.endTurn("Jaap");
        assertTrue(player.getPlayer("Piet").hasTurn());
        player.endTurn("Piet");
        assertTrue(player.getPlayer("Jaap").hasTurn());
    }

    @Test
    public void testNoTurnPlayerAfterAllPassed() {
        player.perform(new PassAction("Henk", true));
        player.perform(new PassAction("Jaap", false));
        player.perform(new PassAction("Piet", false));
        assertEquals(0, player.getAllPlayers().stream().filter(p -> p.hasTurn())
                .count());
    }

    @Test
    public void testEndOfRound() {
        player.endTurn("Henk");
        player.perform(new PassAction("Jaap", true));
        player.endTurn("Piet");
        player.perform(new PassAction("Henk", false));
        player.perform(new PassAction("Piet", false));

        assertEquals(0, player.getAllPlayers().stream().filter(p -> p.hasTurn())
                .count());

        player.startNewRound();
        assertEquals(1, player.getAllPlayers().stream().filter(p -> p.hasTurn())
                .count());
        assertEquals("Jaap", player.getAllPlayers().stream()
                .filter(p -> p.hasTurn()).findFirst().get().getName());
    }

    @Test
    public void testReverseTurn() {
        player.endTurn("Henk");
        player.endTurnReverse("Jaap");
        assertTrue(player.getPlayer("Henk").hasTurn());
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
                player.getPlayer("Henk").getShippingImprovementCost());
        assertEquals(new Resource(5, 2, 1),
                player.getPlayer("Henk").getShovelImprovementCost());

        IntStream.range(0, 3).forEach(i -> player
                .perform(new ShippingAction("Henk", new Resource(0, 0, 0), 0)));

        IntStream.range(0, 2)
                .forEach(i -> player.perform(new ShovelAction("Henk",
                        new Resource(0, 0, 0), new Resource(0, 0, 0))));

        assertEquals(3, player.getPlayer("Henk").getShippingRange());
        assertArrayEquals((new Resource(0, 1, 0)).toArray(),
                player.getPlayer("Henk").getTerraformCost());
        assertNull(player.getPlayer("Henk").getShippingImprovementCost());
        assertNull(player.getPlayer("Henk").getShovelImprovementCost());
    }

}
