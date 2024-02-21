package terra.domain;

import java.util.List;

import terra.domain.actions.TileAction;

public interface ITile extends ITileInfo, ITileActionInfo {

    List<ITile> getTileList();

    ITile getTile(TileLocation location);

    void perform(TileAction action);

    int getAmountOfBuildingsOn(Terrain terrain);

}
