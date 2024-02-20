package terra.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import terra.domain.actions.GameAction;
import terra.domain.actions.PlayerAction;
import terra.domain.actions.TileAction;

public class TerraMystica implements ITerraMystica {

    private final ActionBuilder actionBuilder;

    private GamePhase gamePhase;
    private int roundNumber;

    private final int boardSize;
    private Tile rootTile;
    private Player player;

    public TerraMystica(Player player, Terrain[] board, int boardSize) {
        this.actionBuilder = new ActionBuilder(this);
        this.gamePhase = GamePhase.GAME_START;
        this.roundNumber = 0;
        this.boardSize = boardSize;
        List<Tile> tiles = IntStream.range(0, board.length)
                .mapToObj(i -> new Tile(
                        TileLocation.fromBoardIndex(i, this.boardSize),
                        board[i]))
                .toList();
        tiles.stream().forEach(t -> t.setAdjacent(tiles));
        this.rootTile = tiles.get(0);
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
        case GAME_START:
            return "Setup";
        case GAME_START_REVERSE:
            return "Setup";
        default:
            return "";
        }
    }

    public List<IPlayerInfo> getPlayerInfo() {
        List<IPlayerInfo> list = new ArrayList<IPlayerInfo>();
        list.addAll(player.getAllPlayers());
        return list;
    }

    public Player getPlayer(String name) {
        return player.getAllPlayers().stream()
                .filter(p -> p.getName().equals(name)).findFirst().get();
    }

    public Player getTurnPlayer() {
        return player.getAllPlayers().stream().filter(p -> p.hasTurn())
                .findFirst().get();
    }

    public Resource getPlayerBuildingCost(String name, Building building,
            boolean adjacent) {
        return player.getBuildingCost(name, building, adjacent);
    }

    public Resource getPlayerTerraformCost(String name, int steps) {
        return player.getTerraformCost(name, steps);
    }

    public Resource getPlayerImprovementCost(String name, String type) {
        return player.getPlayerImprovementCost(name, type);
    }

    public boolean playerCanPayCost(String name, Resource cost) {
        return player.canPayForCost(name, cost);
    }

    public boolean playerCanBuildBuilding(String name, Building building) {
        return player.canBuildBuilding(name, building);
    }

    public int[][] getTileLocations() {
        return rootTile.getTileLocations();
    }

    public Terrain getTileTerrain(int[] location) {
        return rootTile.getTileTerrain(TileLocation.fromArray(location));
    }

    public Building getTileBuilding(int[] location) {
        return rootTile.getTileBuilding(TileLocation.fromArray(location));
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
//        list.addAll(rootTile.getTileActions(playerName,
//                TileLocation.fromArray(location), actionBuilder));
        list.addAll(actionBuilder.getTileActions(getPlayer(name),
                rootTile.findTile(TileLocation.fromArray(location))));
        return list;
    }

    public void perform(GameAction action) {
        if (getPlayer(action.getPlayerName()).hasTurn()) {
            if (action instanceof PlayerAction) {
                player.perform((PlayerAction) action);
            }
            if (action instanceof TileAction) {
                rootTile.perform((TileAction) action);
                if (getTileBuilding(((TileAction) action).getLocation())
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
        player.getAllPlayers().forEach(p -> player.gainIncome(p.getName()));
    }

    protected void setGamePhase(GamePhase phase) {
        this.gamePhase = phase;
    }

}
