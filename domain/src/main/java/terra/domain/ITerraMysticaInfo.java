package terra.domain;

import java.util.List;

import terra.domain.actions.GameAction;

public interface ITerraMysticaInfo {

    String getGamePhaseMessage();

    List<IPlayerInfo> getPlayerInfo();

    IPlayerInfo getPlayer(String name);

    IPlayerInfo getTurnPlayer();

    List<ITileInfo> getTileInfo();

    ITileInfo getTile(int[] location);

    GameAction getPassAction(String playerName);

    GameAction getShippingAction(String playerName);

    GameAction getShovelAction(String playerName);

    List<GameAction> getTileActions(String playerName, int[] location);

}
