package terra.domain;

import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;

public class ActionBuilder {

    private ITerraMystica game;

    protected ActionBuilder(ITerraMystica game) {
        this.game = game;
    }

    public GameAction getPassAction(String playerName) {
        if (game.playerHasTurn(playerName)) {
            return new PassAction(playerName, true);
        }
        return null;
    }

}
