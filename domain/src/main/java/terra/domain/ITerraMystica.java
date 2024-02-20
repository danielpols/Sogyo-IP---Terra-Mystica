package terra.domain;

import java.util.List;

import terra.domain.actions.GameAction;

public interface ITerraMystica {

    GamePhase getGamePhase();

    String getGamePhaseMessage();

    List<IPlayerInfo> getPlayerInfo();

    IPlayerInfo getPlayer(String name);

    Resource getPlayerBuildingCost(String name, Building building,
            boolean adjacent);

    Resource getPlayerTerraformCost(String name, int steps);

    Resource getPlayerImprovementCost(String name, String type);

    boolean playerCanPayCost(String name, Resource cost);

    boolean playerCanBuildBuilding(String name, Building building);

    int[][] getTileLocations();

    Terrain getTileTerrain(int[] location);

    Building getTileBuilding(int[] location);

    GameAction getPassAction(String playerName);

    GameAction getShippingAction(String playerName);

    GameAction getShovelAction(String playerName);

    List<GameAction> getTileActions(String playerName, int[] location);

    void perform(GameAction action);

    void endTurn(String playerName);

    void startNewRoundIfAllPassed();

}
