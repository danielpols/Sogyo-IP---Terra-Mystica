package terra.domain;

import java.util.ArrayList;
import java.util.List;

import terra.domain.actions.BuildAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.TileAction;
import terra.domain.actions.UpgradeAction;

public class ActionBuilder {

    private ITerraMystica game;

    protected ActionBuilder(ITerraMystica game) {
        this.game = game;
    }

    public PassAction getPassAction(IPlayerActionInfo player) {
        if (player.hasTurn()) {
            return new PassAction(player.getName(), true);
        }
        return null;
    }

    public ShippingAction getShippingAction(IPlayerActionInfo player) {
        if (player.hasTurn()) {
            Resource cost = player.getShippingImprovementCost();
            if (player.canPayCost(cost)) {
                return new ShippingAction(player.getName(), cost,
                        player.getShippingRange() + 1);
            }
        }
        return null;
    }

    public ShovelAction getShovelAction(IPlayerActionInfo player) {
        if (player.hasTurn()) {
            Resource cost = player.getShovelImprovementCost();
            if (player.canPayCost(cost)) {
                return new ShovelAction(player.getName(), cost,
                        new Resource(0, 0, 0));
            }
        }
        return null;
    }

    public List<TileAction> getTileActions(IPlayerActionInfo player,
            ITileActionInfo tile) {
        List<TileAction> list = new ArrayList<TileAction>();
        list.addAll(getBuildAction(player, tile));
        list.addAll(getUpgradeActions(player, tile));
        return list;
    }

    public List<BuildAction> getBuildAction(IPlayerActionInfo player,
            ITileActionInfo tile) {
        List<BuildAction> list = new ArrayList<BuildAction>();
        if (tile.hasBuilding()) {
            return list;
        }

        Terrain playerTerrain = player.getTerrain();
        Terrain tileTerrain = tile.getTerrain();

        if (tileTerrain.equals(Terrain.RIVER)) {
            return list;
        }

        boolean buildableDuringRound = game.getGamePhase()
                .equals(GamePhase.GAME_ROUND)
                && tile.isIndirectlyAdjacentTo(playerTerrain,
                        player.getShippingRange());

        boolean buildableDuringSetup = (game.getGamePhase()
                .equals(GamePhase.GAME_START)
                || game.getGamePhase().equals(GamePhase.GAME_START_REVERSE))
                && tileTerrain.equals(playerTerrain);

        Resource buildCost = game.getGamePhase().equals(GamePhase.GAME_ROUND)
                ? player.getBuildingCost(Building.DWELLING, false)
                : new Resource(0, 0, 0);

        buildCost = buildCost.add(player.getTerraformCost(tileTerrain));

        if (!player.canPayCost(buildCost)
                || !player.canBuildBuilding(Building.DWELLING)) {
            return list;
        }

        if (buildableDuringRound || buildableDuringSetup) {
            list.add(new BuildAction(player.getName(), buildCost,
                    tile.getLocation(), playerTerrain, Building.DWELLING,
                    tileTerrain.distanceTo(playerTerrain).getAsInt()));
        }
        return list;
    }

    public List<UpgradeAction> getUpgradeActions(IPlayerActionInfo player,
            ITileActionInfo tile) {
        List<UpgradeAction> list = new ArrayList<UpgradeAction>();

        if (tile.hasBuilding() && tile.getTerrain().equals(player.getTerrain())
                && game.getGamePhase().equals(GamePhase.GAME_ROUND)) {
            Building tileBuilding = tile.getBuilding();
            boolean adjacencyBonus = tile.hasAdjacencyBonus();
            list.addAll(tileBuilding.upgrades().stream()
                    .filter(b -> player.canPayCost(
                            player.getBuildingCost(b, adjacencyBonus))
                            && player.canBuildBuilding(b))
                    .map(b -> new UpgradeAction(player.getName(),
                            player.getBuildingCost(b, adjacencyBonus),
                            tile.getLocation(), tileBuilding, b))
                    .toList());
        }

        return list;
    }

}
