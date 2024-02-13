package terra.domain;

import java.util.List;

import terra.domain.actions.GameAction;

public interface ITerraMystica {

    List<String> getPlayerNames();

    Terrain getPlayerTerrain(String name);

    boolean playerHasTurn(String name);

    boolean playerHasPassed(String name);

    boolean isStartingPlayer(String name);

    int[][] getTileLocations();

    Terrain getTileTerrain(int[] location);

    Building getTileBuilding(int[] location);

    boolean tileIsBuildable(int[] location);

    GameAction getPassAction(String playerName);

    void perform(GameAction action);

}
