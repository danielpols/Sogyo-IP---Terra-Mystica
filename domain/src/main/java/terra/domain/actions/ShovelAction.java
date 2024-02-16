package terra.domain.actions;

import terra.domain.Resource;

public class ShovelAction extends PlayerAction {

    private final Resource newCost;

    public ShovelAction(String name, Resource cost, Resource newCost) {
        super(name, cost);
        this.newCost = newCost;
    }

    public Resource getNewCost() {
        return newCost;
    }

}
