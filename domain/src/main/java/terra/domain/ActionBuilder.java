package terra.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import terra.domain.actions.BuildAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.UpgradeAction;

public class ActionBuilder {

    private ITerraMystica game;

    protected ActionBuilder(ITerraMystica game) {
        this.game = game;
    }

    public PassAction getPassAction(String playerName) {
        if (game.playerHasTurn(playerName)) {
            return new PassAction(playerName, true);
        }
        return null;
    }

    public List<BuildAction> getBuildAction(String playerName, Tile tile) {
        List<BuildAction> list = new ArrayList<BuildAction>();
        if (tile.hasBuilding()) {
            return list;
        }

        Terrain playerTerrain = game.getPlayerTerrain(playerName);
        Terrain tileTerrain = getTerrain(tile);

        OptionalInt terraformCost = tileTerrain.distanceTo(playerTerrain);

        if (terraformCost.isEmpty()) {
            return list;
        }

        boolean buildableDuringRound = game.getGamePhase()
                .equals(GamePhase.GAME_ROUND)
                && tile.isIndirectlyAdjacentTo(playerTerrain,
                        game.getPlayerShippingRange(playerName));

        boolean buildableDuringSetup = (game.getGamePhase()
                .equals(GamePhase.GAME_START)
                || game.getGamePhase().equals(GamePhase.GAME_START_REVERSE))
                && tileTerrain.equals(playerTerrain);

        Resource buildCost = game.getGamePhase().equals(GamePhase.GAME_ROUND)
                ? game.getPlayerBuildingCost(playerName, Building.DWELLING,
                        false)
                : new Resource(0, 0, 0);

        if (buildableDuringRound || buildableDuringSetup) {
            list.add(new BuildAction(playerName, buildCost, tile.getLocation(),
                    playerTerrain, Building.DWELLING,
                    terraformCost.getAsInt()));
        }
        return list;
    }

    public List<UpgradeAction> getUpgradeActions(String playerName, Tile tile) {
        List<UpgradeAction> list = new ArrayList<UpgradeAction>();

        if (tile.hasBuilding()
                && getTerrain(tile).equals(game.getPlayerTerrain(playerName))
                && game.getGamePhase().equals(GamePhase.GAME_ROUND)) {
            Building tileBuilding = game.getTileBuilding(tile.getLocation());
            boolean tileAdjacentToOpponent = tile.getAdjacent().stream()
                    .filter(t -> t.hasBuilding()
                            && !getTerrain(t).equals(getTerrain(tile)))
                    .count() > 0;
            list.addAll(tileBuilding.upgrades().stream()
                    .map(b -> new UpgradeAction(playerName,
                            game.getPlayerBuildingCost(playerName, b,
                                    tileAdjacentToOpponent),
                            tile.getLocation(), tileBuilding, b))
                    .toList());
        }

        return list;
    }

    private Terrain getTerrain(Tile t) {
        return game.getTileTerrain(t.getLocation());
    }

}
