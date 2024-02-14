package terra.api.models;

import terra.domain.Building;
import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public class TileDTO {

    private Terrain terrain;
    private Building building;
    private ActionDTO[] actions;

    public TileDTO(ITerraMystica game, int[] location) {
        this.terrain = game.getTileTerrain(location);
        this.building = game.getTileBuilding(location);
        this.actions = game
                .getTileActions(game.getPlayerNames().stream()
                        .filter(n -> game.playerHasTurn(n)).findFirst().get(),
                        location)
                .stream().map(a -> ActionDTO.getActionDTO(a))
                .toArray(ActionDTO[]::new);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Building getBuilding() {
        return building;
    }

    public ActionDTO[] getActions() {
        return actions;
    }

}
