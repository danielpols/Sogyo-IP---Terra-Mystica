package terra.api.models;

import org.junit.jupiter.api.Test;

import terra.domain.ITerraMystica;
import terra.domain.MockTerraMystica;

public class GameDTOTest {

    @Test
    public void testAllTilesPresent() {
        ITerraMystica game = new MockTerraMystica();
        GameDTO dto = new GameDTO(game);
    }

}
