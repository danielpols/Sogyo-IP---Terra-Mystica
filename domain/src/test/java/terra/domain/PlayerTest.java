package terra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    public void testCanFindTurnPlayer() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        Player player = new Player(names, terrains);
        assertSame(player, player.getTurnPlayer());
        assertSame(player, player.getNextPlayer().getTurnPlayer());
    }

    @Test
    public void testActivePlayerCanPass() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        Player player = new Player(names, terrains);
        assertSame(player, player.getTurnPlayer());
        player.getTurnPlayer().passTurn();
        assertSame(player.getNextPlayer(), player.getTurnPlayer());
        player.getTurnPlayer().getNextPlayer().passTurn();
        assertSame(player.getNextPlayer(), player.getTurnPlayer());
    }

    @Test
    public void testOnlyOneActivePlayer() {
        List<String> names = Arrays.asList("Henk", "Jaap", "Piet");
        List<Terrain> terrains = Arrays.asList(Terrain.DESERT, Terrain.PLAINS,
                Terrain.SWAMP);
        Player player = new Player(names, terrains);
        player.getTurnPlayer().passTurn();
        assertEquals(1, player.getAllPlayerNames().stream()
                .filter(n -> player.playerHasTurn(n)).count());
    }

}
