package terra.domain;

import terra.domain.actions.GameAction;

public interface ITerraMystica extends ITerraMysticaInfo {

    GamePhase getGamePhase();

    void perform(GameAction action);

    void endTurn(String playerName);

    void startNewRoundIfAllPassed();

}
