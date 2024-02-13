package terra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
