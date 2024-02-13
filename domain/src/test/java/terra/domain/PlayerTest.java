package terra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testCanGetAllPlayers() {
        List<String> names = Arrays.asList("Henk", "Jaap");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS);
        Player player = new Player(names, terrains);
        assertEquals(2, player.getAllPlayerNames().size());
    }

    @Test
    public void testActivePlayerCanPass() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        Player player = new Player(names, terrains);
        assertTrue(player.playerHasTurn("Henk"));
        player.endTurn("Henk");
        assertTrue(player.playerHasTurn("Jaap"));
        assertFalse(player.playerHasTurn("Henk"));
        player.endTurn("Piet");
        assertTrue(player.playerHasTurn("Jaap"));
    }

    @Test
    public void testOnlyOneActivePlayer() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        Player player = new Player(names, terrains);
        player.endTurn("Henk");
        assertEquals(1, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());
    }

}
