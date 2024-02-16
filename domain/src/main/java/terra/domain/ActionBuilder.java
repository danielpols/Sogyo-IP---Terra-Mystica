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
        Terrain tileTerrain = game.getTileTerrain(tile.getLocation());

        OptionalInt terraformCost = tileTerrain.distanceTo(playerTerrain);

        if (terraformCost.isEmpty()) {
            return list;
        }

        if (game.getGamePhase().equals(GamePhase.GAME_ROUND)) {
            if (tile.isIndirectlyAdjacentTo(playerTerrain,
                    game.getPlayerShippingRange(playerName))) {
                list.add(new BuildAction(playerName, tile.getLocation(),
                        playerTerrain, Building.DWELLING,
                        terraformCost.getAsInt()));
            }
        } else if (!game.getGamePhase().equals(GamePhase.GAME_END)) {
            if (tileTerrain.equals(playerTerrain)) {
                list.add(new BuildAction(playerName, tile.getLocation(),
                        playerTerrain, Building.DWELLING,
                        terraformCost.getAsInt()));
            }
        }
        return list;
    }

    public List<UpgradeAction> getUpgradeActions(String playerName, Tile tile) {
        List<UpgradeAction> list = new ArrayList<UpgradeAction>();

        if (tile.hasBuilding()
                && game.getTileTerrain(tile.getLocation())
                        .equals(game.getPlayerTerrain(playerName))
                && game.getGamePhase().equals(GamePhase.GAME_ROUND)) {
            Building tileBuilding = game.getTileBuilding(tile.getLocation());
            list.addAll(
                    tileBuilding.upgrades().stream()
                            .map(b -> new UpgradeAction(playerName,
                                    tile.getLocation(), tileBuilding, b))
                            .toList());
        }

        return list;
    }

}
