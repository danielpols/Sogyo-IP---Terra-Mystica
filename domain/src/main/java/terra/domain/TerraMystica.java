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

    public List<String> getPlayerNames() {
        return player.getAllPlayerNames();
    }

    public Terrain getPlayerTerrain(String name) {
        return player.getPlayerTerrain(name);
    }

    public boolean playerHasTurn(String name) {
        return player.playerHasTurn(name);
    }

    public boolean playerHasPassed(String name) {
        return player.playerHasPassed(name);
    }

    public boolean isStartingPlayer(String name) {
        return player.isStartingPlayer(name);
    }

    public int getPlayerShippingRange(String name) {
        return player.getPlayerShippingRange(name);
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
            return actionBuilder.getPassAction(playerName);
        }
        return null;
    }

    public List<GameAction> getTileActions(String playerName, int[] location) {
        List<GameAction> list = new ArrayList<GameAction>();
        list.addAll(rootTile.getTileActions(playerName,
                TileLocation.fromArray(location), actionBuilder));
        return list;
    }

    public void perform(GameAction action) {
        if (action instanceof PlayerAction) {
            player.perform((PlayerAction) action);
        }
        if (action instanceof TileAction) {
            rootTile.perform((TileAction) action);
        }
    }

    public void endTurn(String playerName) {
        player.endTurn(playerName);
        if (gamePhase == GamePhase.GAME_START && player.getAllPlayerNames()
                .stream()
                .filter(n -> rootTile
                        .getAmountOfBuildingsOn(getPlayerTerrain(n)) != 1)
                .count() == 0) {
            gamePhase = GamePhase.GAME_START_REVERSE;
        }
        if (gamePhase == GamePhase.GAME_START_REVERSE && player
                .getAllPlayerNames().stream()
                .filter(n -> rootTile
                        .getAmountOfBuildingsOn(getPlayerTerrain(n)) != 2)
                .count() == 0) {
            gamePhase = GamePhase.GAME_ROUND;
        }
    }

    public void startNewRoundIfAllPassed() {
        if (player.getAllPlayerNames().stream().filter(n -> !playerHasPassed(n))
                .count() == 0) {
            player.startNewRound();
            roundNumber++;
        }
        if (roundNumber == 6) {
            gamePhase = GamePhase.GAME_END;
        }
    }

    protected void setGamePhase(GamePhase phase) {
        this.gamePhase = phase;
    }

}
