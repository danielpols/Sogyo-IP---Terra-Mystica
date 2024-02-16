package terra.api.models.subactions;

import terra.api.models.ActionDTO;
import terra.domain.actions.ShippingAction;

public class ShippingActionDTO extends ActionDTO {

    private int newRange;

    public ShippingActionDTO() {

    }

    public ShippingActionDTO(ShippingAction action) {
        super(action);
        newRange = action.getNewRange();
    }

    public ShippingAction toAction() {
        return new ShippingAction(getPlayerName(), costResource(), newRange);
    }

}
