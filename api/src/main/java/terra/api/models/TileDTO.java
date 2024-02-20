package terra.api.models;

import terra.domain.Building;
import terra.domain.GamePhase;
import terra.domain.ITerraMystica;
import terra.domain.Terrain;

public class TileDTO {

    private Terrain terrain;
    private Building building;
    private ActionDTO[] actions;

    public TileDTO(ITerraMystica game, int[] location) {
        this.terrain = game.getTileTerrain(location);
        this.building = game.getTileBuilding(location);
        if (game.getGamePhase() != GamePhase.GAME_END) {
            this.actions = game
                    .getTileActions(game.getPlayerInfo().stream()
                            .filter(p -> p.hasTurn()).findFirst().get()
                            .getName(), location)
                    .stream().map(a -> ActionDTO.getActionDTO(a))
                    .toArray(ActionDTO[]::new);
        } else {
            this.actions = new ActionDTO[0];
        }
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
