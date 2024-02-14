package terra.api.models;

import terra.domain.actions.GameAction;

public abstract class ActionDTO {

    private String playerName;

    public ActionDTO(GameAction action) {
        playerName = action.getPlayerName();
    }

    public void setPlayerName(String name) {
        playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

}
