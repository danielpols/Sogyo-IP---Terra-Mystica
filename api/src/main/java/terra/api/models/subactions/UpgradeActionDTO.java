package terra.api.models.subactions;

import terra.api.models.ActionDTO;
import terra.domain.Building;
import terra.domain.actions.UpgradeAction;

public class UpgradeActionDTO extends ActionDTO {

    private int[] location;
    private Building sourceBuilding;
    private Building targetBuilding;

    public UpgradeActionDTO() {

    }

    public UpgradeActionDTO(UpgradeAction action) {
        super(action);
        this.setLocation(action.getLocation());
        this.setTargetBuilding(action.getTargetBuilding());
        this.setSourceBuilding(action.getSourceBuilding());
    }

    public UpgradeAction toAction() {
        return new UpgradeAction(getPlayerName(), costResource(), location,
                sourceBuilding, targetBuilding);
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public Building getTargetBuilding() {
        return targetBuilding;
    }

    public void setTargetBuilding(Building targetBuilding) {
        this.targetBuilding = targetBuilding;
    }

    public Building getSourceBuilding() {
        return sourceBuilding;
    }

    public void setSourceBuilding(Building sourceBuilding) {
        this.sourceBuilding = sourceBuilding;
    }

}
