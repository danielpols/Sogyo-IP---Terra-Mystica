package terra.api.models;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import terra.domain.ITerraMystica;
import terra.domain.TerraMysticaFactory;
import terra.domain.Terrain;
import terra.persistence.ITerraMysticaRepository;
import terra.persistence.MockTerraMysticaDatabase;
import terra.persistence.TerraMysticaRepository;

public class GameDTOTest {

    @Test
    public void testCanSerializeGameDTO() {
        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new MockTerraMysticaDatabase(), new TerraMysticaFactory());

        repository.initialiseGame("yo", Arrays.asList("Daniel", "Gerrit"),
                Arrays.asList(Terrain.PLAINS, Terrain.MOUNTAINS));

        ITerraMystica game = repository.loadGame("yo");
        GameDTO dto = new GameDTO(game);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
