package terra.api.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import terra.domain.ITerraMystica;
import terra.domain.Player;
import terra.domain.TerraMystica;
import terra.domain.Terrain;
import terra.persistence.MockTerraMysticaDatabase;
import terra.persistence.TerraMysticaRepository;

public class GameDTOTest {

    @Test
    public void testCanSerializeGameDTO() {
        ITerraMystica game = new TerraMystica(
                new Player(Arrays.asList("Daniel", "Gerrit"),
                        Arrays.asList(Terrain.LAKE, Terrain.MOUNTAINS)),
                (new TerraMysticaRepository(new MockTerraMysticaDatabase()))
                        .getStartingTerrain(),
                13);
        GameDTO dto = new GameDTO(game);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
