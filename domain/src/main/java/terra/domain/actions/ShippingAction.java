package terra.domain.actions;

import terra.domain.Resource;

public class ShippingAction extends PlayerAction {

    private final int newRange;

    public ShippingAction(String name, Resource cost, int newRange) {
        super(name, cost);
        this.newRange = newRange;
    }

    public int getNewRange() {
        return newRange;
    }

}
