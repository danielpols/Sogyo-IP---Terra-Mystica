package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.Resource;
import terra.domain.actions.GameAction;
import terra.domain.actions.ShovelAction;

@Embeddable
public class PersistableShovelAction extends PersistableGameAction {

    int[] newCost;

    public PersistableShovelAction(ShovelAction action) {
        super(action);
        this.newCost = action.getNewCost().toArray();
    }

    public GameAction toAction() {
        return new ShovelAction(playerName, getResourceCost(),
                new Resource(newCost[0], newCost[1], newCost[2]));
    }

}