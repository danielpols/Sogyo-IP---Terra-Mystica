package terra.persistence;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import terra.domain.Building;
import terra.domain.ITerraMystica;
import terra.domain.Resource;
import terra.domain.TerraMysticaFactory;
import terra.domain.Terrain;
import terra.domain.actions.BuildAction;

public class TerraRepositoryTest {

    @Test
    public void testCanObtainStartingBoard() {

        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new MockTerraMysticaDatabase(), new TerraMysticaFactory());

        repository.initialiseGame("yo", Arrays.asList("Daniel", "Gerrit"),
                Arrays.asList(Terrain.PLAINS, Terrain.MOUNTAINS));

        ITerraMystica game = repository.loadGame("yo");

        Terrain[] terrain = game.getTileInfo().stream().map(t -> t.getTerrain())
                .toArray(Terrain[]::new);

        Terrain[] actual = IntStream.range(0, 5).mapToObj(i -> terrain[i])
                .toArray(Terrain[]::new);

        assertArrayEquals(new Terrain[] { Terrain.PLAINS, Terrain.MOUNTAINS,
                Terrain.FOREST, Terrain.LAKE, Terrain.DESERT }, actual);

    }

    @Test
    public void testCanSaveAndLoadGame() {
        ITerraMysticaRepository repository = new TerraMysticaRepository(
                new MockTerraMysticaDatabase(), new TerraMysticaFactory());
        String id = "bla";

        repository.initialiseGame(id, Arrays.asList("Daniel", "Gerrit"),
                Arrays.asList(Terrain.PLAINS, Terrain.MOUNTAINS));

        repository.saveAction(id,
                new BuildAction("Daniel", new Resource(0, 0, 0),
                        new int[] { 0, 0 }, Terrain.PLAINS, Building.DWELLING,
                        0));

        ITerraMystica game = repository.loadGame(id);

        assertEquals(Building.DWELLING,
                game.getTile(new int[] { 0, 0 }).getBuilding());
        assertTrue(game.getPlayer("Gerrit").hasTurn());
    }

}