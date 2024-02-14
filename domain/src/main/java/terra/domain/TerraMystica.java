package terra.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import terra.domain.actions.GameAction;
import terra.domain.actions.PlayerAction;
import terra.domain.actions.TileAction;

public class TerraMystica implements ITerraMystica {

    private final ActionBuilder actionBuilder;

    private final int boardSize;
    private Tile rootTile;
    private Player player;

    public TerraMystica(Player player, Terrain[] board, int boardSize) {
        this.actionBuilder = new ActionBuilder(this);
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

    public boolean tileIsBuildable(int[] location) {
        return rootTile.isBuildable(TileLocation.fromArray(location), player);
    }

    public GameAction getPassAction(String playerName) {
        return actionBuilder.getPassAction(playerName);
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
    }

    public void startNewRoundIfAllPassed() {
        if (player.getAllPlayerNames().stream().filter(n -> !playerHasPassed(n))
                .count() == 0) {
            player.startNewRound();
        }
    }

}
