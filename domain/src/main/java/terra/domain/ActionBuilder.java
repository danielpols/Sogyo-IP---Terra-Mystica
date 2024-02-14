package terra.domain;

import java.util.ArrayList;
import java.util.List;

import terra.domain.actions.BuildAction;
import terra.domain.actions.PassAction;

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
        Terrain tileTerrain = tile
                .getTileTerrain(TileLocation.fromArray(tile.getLocation()));
        Building tileBuilding = tile
                .getTileBuilding(TileLocation.fromArray(tile.getLocation()));
        if (tile.getAmountOfBuildingsOn(playerTerrain) < 2) {
            if (tileTerrain.equals(playerTerrain)) {
                list.add(new BuildAction(playerName, tile.getLocation(),
                        playerTerrain, Building.DWELLING));
            }
        } else {
            if (tile.isIndirectlyAdjacentTo(playerTerrain,
                    game.getPlayerShippingRange(playerName))) {
                list.addAll(tileBuilding.upgrades().stream()
                        .map(u -> new BuildAction(playerName,
                                tile.getLocation(), playerTerrain, u))
                        .toList());
            }
        }
        return list;
    }

}
