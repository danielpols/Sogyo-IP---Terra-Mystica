package terra.persistence.entities;

import javax.persistence.Embeddable;

import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;

@Embeddable
public class PersistablePassAction extends PersistableGameAction {

    boolean starting;

    public PersistablePassAction(PassAction action) {
        super(action);
        this.starting = action.isStarting();
    }

    public GameAction toAction() {
        return new PassAction(playerName, starting);
    }
}