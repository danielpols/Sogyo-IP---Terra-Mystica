package terra.api.models;

import terra.domain.ITerraMystica;

public class BoardDTO {

    private TileDTO[] tiles;

    public BoardDTO(ITerraMystica game) {

        tiles = game.getTileInfo().stream().map(t -> new TileDTO(game, t))
                .toArray(TileDTO[]::new);

    }

    public TileDTO[] getTiles() {
        return tiles;
    }

}
