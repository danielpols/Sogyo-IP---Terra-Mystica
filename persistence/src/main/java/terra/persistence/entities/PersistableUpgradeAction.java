package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.Building;
import terra.domain.actions.GameAction;
import terra.domain.actions.UpgradeAction;

@Embeddable
public class PersistableUpgradeAction extends PersistableGameAction {

    int[] location;
    Building sourceBuilding;
    Building targetBuilding;

    public PersistableUpgradeAction(UpgradeAction action) {
        super(action);
        this.location = action.getLocation();
        this.sourceBuilding = action.getSourceBuilding();
        this.targetBuilding = action.getTargetBuilding();
    }

    public GameAction toAction() {
        return new UpgradeAction(playerName, getResourceCost(), location,
                sourceBuilding, targetBuilding);
    }

}