package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.Resource;
import terra.domain.actions.GameAction;

@Embeddable
public abstract class PersistableGameAction {

    protected String playerName;
    protected int[] cost;

    protected PersistableGameAction(GameAction action) {
        this.playerName = action.getPlayerName();
        this.cost = action.getCost().toArray();
    }

    protected Resource getResourceCost() {
        return new Resource(cost[0], cost[1], cost[2]);
    }

    public abstract GameAction toAction();

}
