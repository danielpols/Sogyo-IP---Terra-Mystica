package terra.api.models;

import terra.api.models.subactions.BuildActionDTO;
import terra.api.models.subactions.PassActionDTO;
import terra.domain.Building;
import terra.domain.ITerraMystica;
import terra.domain.Terrain;
import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;

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
                .stream().map(a -> getActionDTO(a)).toArray(ActionDTO[]::new);
    }

    private ActionDTO getActionDTO(GameAction a) {
        if (a instanceof PassAction) {
            return new PassActionDTO((PassAction) a);
        }
        if (a instanceof BuildAction) {
            return new BuildActionDTO((BuildAction) a);
        }
        return null;
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
