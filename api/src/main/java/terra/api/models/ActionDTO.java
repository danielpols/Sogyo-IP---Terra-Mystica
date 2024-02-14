package terra.api.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import terra.api.models.subactions.BuildActionDTO;
import terra.api.models.subactions.PassActionDTO;
import terra.domain.actions.GameAction;

@JsonSubTypes(value = { @JsonSubTypes.Type(value = BuildActionDTO.class),
        @JsonSubTypes.Type(value = PassActionDTO.class) })
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public abstract class ActionDTO {

    private String playerName;

    public ActionDTO() {

    }

    public ActionDTO(GameAction action) {
        playerName = action.getPlayerName();
    }

    public abstract GameAction toAction();

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

}
