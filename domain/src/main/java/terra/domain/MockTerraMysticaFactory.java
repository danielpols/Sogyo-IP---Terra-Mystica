package terra.domain;

public class MockTerraMysticaFactory implements ITerraMysticaFactory {

    @Override
    public ITerraMystica startGame(String[] players, Terrain[] terrains) {
        return new MockTerraMystica();
    }

}
