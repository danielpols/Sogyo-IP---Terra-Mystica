package terra.api.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import terra.api.models.subactions.BuildActionDTO;
import terra.api.models.subactions.PassActionDTO;
import terra.api.models.subactions.UpgradeActionDTO;
import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.UpgradeAction;

@JsonSubTypes(value = { @JsonSubTypes.Type(value = BuildActionDTO.class),
        @JsonSubTypes.Type(value = PassActionDTO.class),
        @JsonSubTypes.Type(value = UpgradeActionDTO.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public abstract class ActionDTO {

    private String playerName;

    public ActionDTO() {

    }

    public ActionDTO(GameAction action) {
        playerName = action.getPlayerName();
    }

    public static ActionDTO getActionDTO(GameAction a) {
        if (a instanceof PassAction) {
            return new PassActionDTO((PassAction) a);
        }
        if (a instanceof BuildAction) {
            return new BuildActionDTO((BuildAction) a);
        }
        if (a instanceof UpgradeAction) {
            return new UpgradeActionDTO((UpgradeAction) a);
        }
        return null;
    }

    public abstract GameAction toAction();

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

}
