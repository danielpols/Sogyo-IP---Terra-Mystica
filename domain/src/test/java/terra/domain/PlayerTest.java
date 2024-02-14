package terra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import terra.domain.actions.PassAction;

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
        assertEquals(3, player.getAllPlayerNames().size());
    }

    @Test
    public void testActivePlayerCanEndTurn() {
        assertTrue(player.playerHasTurn("Henk"));
        player.endTurn("Henk");
        assertTrue(player.playerHasTurn("Jaap"));
        assertFalse(player.playerHasTurn("Henk"));
        player.endTurn("Piet");
        assertTrue(player.playerHasTurn("Jaap"));
    }

    @Test
    public void testOnlyOneActivePlayer() {
        player.endTurn("Henk");
        assertEquals(1, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());
    }

    @Test
    public void testEndTurnSkipsPassedPlayers() {
        player.perform(new PassAction("Henk", true));
        player.endTurn("Jaap");
        assertTrue(player.playerHasTurn("Piet"));
        player.endTurn("Piet");
        assertTrue(player.playerHasTurn("Jaap"));
    }

    @Test
    public void testNoTurnPlayerAfterAllPassed() {
        player.perform(new PassAction("Henk", true));
        player.perform(new PassAction("Jaap", false));
        player.perform(new PassAction("Piet", false));
        assertEquals(0, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());
    }

    @Test
    public void testEndOfRound() {
        player.endTurn("Henk");
        player.perform(new PassAction("Jaap", true));
        player.endTurn("Piet");
        player.perform(new PassAction("Henk", false));
        player.perform(new PassAction("Piet", false));

        assertEquals(0, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());

        player.startNewRound();
        assertEquals(1, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());
        assertEquals("Jaap", player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).findFirst().get());
    }

}
