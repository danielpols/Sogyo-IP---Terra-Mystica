package terra.domain.actions;

public abstract class GameAction {

    protected final String playerName;

    protected GameAction(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
