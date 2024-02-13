package terra.domain.actions;

public abstract class GameAction {

    protected final String playerName;

    protected GameAction(String name) {
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }
}
