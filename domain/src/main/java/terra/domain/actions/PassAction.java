package terra.domain.actions;

import terra.domain.Resource;

public class PassAction extends PlayerAction {

    private final boolean starting;

    public PassAction(String name, boolean firstToPass) {
        super(name, new Resource(0, 0, 0));
        this.starting = firstToPass;
    }

    public boolean isStarting() {
        return starting;
    }

}
