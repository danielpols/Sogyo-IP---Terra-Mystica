package terra.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import terra.domain.ITerraMystica;
import terra.domain.Player;
import terra.domain.TerraMystica;
import terra.domain.Terrain;

public class TerraRepositoryTest {

    @Test
    public void testCanObtainStartingBoard() {

        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new MockTerraMysticaDatabase());

        Terrain[] terrain = repository.getStartingTerrain();

        Terrain[] actual = IntStream.range(0, 5).mapToObj(i -> terrain[i])
                .toArray(Terrain[]::new);

        assertArrayEquals(new Terrain[] { Terrain.PLAINS, Terrain.MOUNTAINS,
                Terrain.FOREST, Terrain.LAKE, Terrain.DESERT }, actual);

    }

    @Test
    public void testCanSaveAndLoadGame() {
        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new MockTerraMysticaDatabase());

        ITerraMystica game = new TerraMystica(
                new Player(Arrays.asList("Daniel", "Gerrit"),
                        Arrays.asList(Terrain.LAKE, Terrain.MOUNTAINS)),
                repository.getStartingTerrain(), 13);
        String id = "bla";

        repository.saveGame(id, game);

        assertSame(game, repository.loadGame(id));
    }

}