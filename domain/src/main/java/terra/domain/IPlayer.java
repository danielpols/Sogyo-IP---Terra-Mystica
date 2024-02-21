package terra.domain;

import java.util.List;

import terra.domain.actions.GameAction;

interface IPlayer extends IPlayerInfo, IPlayerActionInfo {

    List<IPlayer> getPlayerList();

    IPlayer getPlayer(String name);

    IPlayer getTurnPlayer();

    void perform(GameAction action);

    void endTurn(String name);

    void endTurnReverse(String name);

    void gainIncome();

    void startNewRound();

}
