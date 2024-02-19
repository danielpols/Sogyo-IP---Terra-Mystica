package terra.api.models.subactions;

import terra.api.models.ActionDTO;
import terra.domain.Resource;
import terra.domain.actions.ShovelAction;

public class ShovelActionDTO extends ActionDTO {

    private int[] newCost;

    public ShovelActionDTO() {

    }

    public ShovelActionDTO(ShovelAction action) {
        super(action);
        Resource cost = action.getNewCost();
        this.newCost = new int[] { cost.coin(), cost.worker(), cost.priest() };
    }

    public ShovelAction toAction() {
        return new ShovelAction(getPlayerName(), costResource(),
                new Resource(newCost[0], newCost[1], newCost[2]));
    }

    public int[] getNewCost() {
        return newCost;
    }

    public void setNewCost(int[] newCost) {
        this.newCost = newCost;
    }

}
