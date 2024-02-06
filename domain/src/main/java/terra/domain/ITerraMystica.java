package terra.domain;

public interface ITerraMystica {

    int[][] getTileLocations();

    Terrain getTileTerrain(int[] location);

    Player[] getPlayers();

}
