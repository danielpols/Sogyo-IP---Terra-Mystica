package terra.domain.actions;

public class PassAction extends PlayerAction {

    private final boolean starting;

    public PassAction(String name, boolean firstToPass) {
        super(name);
        this.starting = firstToPass;
    }

    public boolean isStarting() {
        return starting;
    }

}
