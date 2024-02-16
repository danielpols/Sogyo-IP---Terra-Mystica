package terra.api.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import terra.api.models.subactions.BuildActionDTO;
import terra.api.models.subactions.PassActionDTO;
import terra.api.models.subactions.UpgradeActionDTO;
import terra.domain.Resource;
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
    private int[] cost;

    public ActionDTO() {

    }

    public ActionDTO(GameAction action) {
        playerName = action.getPlayerName();
        setCost(new int[] { action.getCost().coin(), action.getCost().worker(),
                action.getCost().priest() });
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

    protected Resource costResource() {
        return new Resource(cost[0], cost[1], cost[2]);
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int[] getCost() {
        return cost;
    }

    public void setCost(int[] cost) {
        this.cost = cost;
    }

}
