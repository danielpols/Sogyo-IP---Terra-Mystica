package terra.domain.actions;

import terra.domain.Resource;

public abstract class GameAction {

    private final String playerName;
    private final Resource cost;

    protected GameAction(String playerName, Resource cost) {
        this.playerName = playerName;
        this.cost = cost;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Resource getCost() {
        return cost;
    }
}
