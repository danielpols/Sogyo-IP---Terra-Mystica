package terra.domain;

public interface ITerraMystica {

    int[][] getTileLocations();

    Terrain getTileTerrain(int[] location);

    Building getTileBuilding(int[] location);

    Player[] getPlayers();

    void passTurn();

    void build(int[] location, Building building);

}
