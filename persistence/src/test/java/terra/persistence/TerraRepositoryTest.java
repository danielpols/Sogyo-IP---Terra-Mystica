package terra.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

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

}