package terra.api.models;

import terra.domain.Building;
import terra.domain.ITerraMysticaInfo;
import terra.domain.ITileInfo;
import terra.domain.Terrain;

public class TileDTO {

    private Terrain terrain;
    private Building building;
    private ActionDTO[] actions;

    public TileDTO(ITerraMysticaInfo game, ITileInfo tile) {
        this.terrain = tile.getTerrain();
        this.building = tile.getBuilding();
        this.actions = game
                .getTileActions(game.getTurnPlayer().getName(),
                        tile.getLocation())
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
