package terra.domain;

import java.util.List;

import terra.domain.actions.GameAction;

public interface ITerraMystica {

    GamePhase getGamePhase();

    String getGamePhaseMessage();

    List<IPlayerInfo> getPlayerInfo();

    IPlayerInfo getPlayer(String name);

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
