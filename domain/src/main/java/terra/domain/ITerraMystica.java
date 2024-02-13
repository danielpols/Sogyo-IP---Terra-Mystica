package terra.domain;

import java.util.List;

public interface ITerraMystica {

    List<String> getPlayerNames();

    Terrain getPlayerTerrain(String name);

    boolean playerHasTurn(String name);

    int[][] getTileLocations();

    Terrain getTileTerrain(int[] location);

    Building getTileBuilding(int[] location);

    boolean tileIsBuildable(int[] location);

    void passTurn();

    void build(int[] location, Building building);

}
