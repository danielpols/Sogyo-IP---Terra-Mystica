package terra.domain;

import java.util.ArrayList;
import java.util.List;

import terra.domain.actions.GameAction;
import terra.domain.actions.PlayerAction;
import terra.domain.actions.TileAction;

public class TerraMystica implements ITerraMystica {

    private final ActionBuilder actionBuilder;

    private GamePhase gamePhase;
    private int roundNumber;

    private ITile rootTile;
    private IPlayer player;

    public TerraMystica(IPlayer player, ITile rootTile) {
        this.actionBuilder = new ActionBuilder(this);
        this.gamePhase = GamePhase.GAME_START;
        this.roundNumber = 0;
        this.rootTile = rootTile;
        this.player = player;
    }

    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public String getGamePhaseMessage() {
        switch (gamePhase) {
        case GAME_END:
            return "Game has ended";
        case GAME_ROUND:
            return "Round " + (roundNumber + 1);
        default:
            return "Setup";
        }
    }

    public List<IPlayerInfo> getPlayerInfo() {
        List<IPlayerInfo> list = new ArrayList<IPlayerInfo>();
        list.addAll(player.getPlayerList());
        return list;
    }

    public IPlayer getPlayer(String name) {
        return player.getPlayer(name);
    }

    public IPlayerInfo getTurnPlayer() {
        return player.getTurnPlayer();
    }

    public List<ITileInfo> getTileInfo() {
        List<ITileInfo> list = new ArrayList<ITileInfo>();
        list.addAll(rootTile.getTileList());
        return list;
    }

    public ITileInfo getTile(int[] location) {
        return rootTile.getTile(TileLocation.fromArray(location));
    }

    public GameAction getPassAction(String playerName) {
        if (gamePhase == GamePhase.GAME_ROUND) {
            return actionBuilder.getPassAction(getPlayer(playerName));
        }
        return null;
    }

    public GameAction getShippingAction(String playerName) {
        if (gamePhase == GamePhase.GAME_ROUND) {
            return actionBuilder.getShippingAction(getPlayer(playerName));
        }
        return null;
    }

    public GameAction getShovelAction(String playerName) {
        if (gamePhase == GamePhase.GAME_ROUND) {
            return actionBuilder.getShovelAction(getPlayer(playerName));
        }
        return null;
    }

    public List<GameAction> getTileActions(String name, int[] location) {
        List<GameAction> list = new ArrayList<GameAction>();
        list.addAll(actionBuilder.getTileActions(getPlayer(name),
                rootTile.getTile(TileLocation.fromArray(location))));
        return list;
    }

    public void perform(GameAction action) {
        if (getPlayer(action.getPlayerName()).hasTurn()) {
            if (action instanceof PlayerAction) {
                player.perform((PlayerAction) action);
            }
            if (action instanceof TileAction) {
                rootTile.perform((TileAction) action);
                if (getTile(((TileAction) action).getLocation()).getBuilding()
                        .equals(((TileAction) action).getTargetBuilding())) {
                    player.perform((TileAction) action);
                }
            }
        }
    }

    public void endTurn(String playerName) {
        if (gamePhase == GamePhase.GAME_START_REVERSE) {
            player.endTurnReverse(playerName);
        } else {
            player.endTurn(playerName);
        }
        if (gamePhase == GamePhase.GAME_START && getPlayerInfo().stream()
                .filter(p -> rootTile
                        .getAmountOfBuildingsOn(p.getTerrain()) != 1)
                .count() == 0) {
            gamePhase = GamePhase.GAME_START_REVERSE;
            player.endTurnReverse(getTurnPlayer().getName());
        }
        if (gamePhase == GamePhase.GAME_START_REVERSE
                && getPlayerInfo().stream()
                        .filter(p -> rootTile
                                .getAmountOfBuildingsOn(p.getTerrain()) != 2)
                        .count() == 0) {
            gamePhase = GamePhase.GAME_ROUND;
            player.endTurn(getTurnPlayer().getName());
            allPlayersTakeIncome();
        }
    }

    public void startNewRoundIfAllPassed() {
        if (getPlayerInfo().stream().filter(p -> !p.hasPassed()).count() == 0) {
            player.startNewRound();
            allPlayersTakeIncome();
            roundNumber++;
        }
        if (roundNumber == 6) {
            gamePhase = GamePhase.GAME_END;
        }
    }

    private void allPlayersTakeIncome() {
        player.getPlayerList().forEach(p -> p.gainIncome());
    }

    protected void setGamePhase(GamePhase phase) {
        this.gamePhase = phase;
    }

}
