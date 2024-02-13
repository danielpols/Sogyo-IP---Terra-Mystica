package terra.domain.actions;

public abstract class PlayerAction extends GameAction {

    protected final String playerName;

    protected PlayerAction(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

}
