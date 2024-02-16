package terra.domain.actions;

import terra.domain.Resource;

public abstract class PlayerAction extends GameAction {

    protected PlayerAction(String name, Resource cost) {
        super(name, cost);
    }

}
