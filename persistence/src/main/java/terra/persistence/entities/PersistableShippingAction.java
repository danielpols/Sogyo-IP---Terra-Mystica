package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.actions.GameAction;
import terra.domain.actions.ShippingAction;

@Embeddable
public class PersistableShippingAction extends PersistableGameAction {

    int newRange;

    public PersistableShippingAction(ShippingAction action) {
        super(action);
        this.newRange = action.getNewRange();
    }

    public GameAction toAction() {
        return new ShippingAction(playerName, getResourceCost(), newRange);
    }

}