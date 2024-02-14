package terra.api.models.subactions;

import terra.api.models.ActionDTO;
import terra.domain.actions.PassAction;

public class PassActionDTO extends ActionDTO {

    private boolean starting;

    public PassActionDTO() {

    }

    public PassActionDTO(PassAction action) {
        super(action);
        starting = action.isStarting();
    }

    public PassAction toAction() {
        return new PassAction(getPlayerName(), starting);
    }

    public void setStarting(boolean value) {
        starting = value;
    }

    public boolean getStarting() {
        return starting;
    }

}
