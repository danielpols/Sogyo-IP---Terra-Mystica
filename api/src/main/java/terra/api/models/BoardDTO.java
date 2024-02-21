package terra.api.models;

import terra.domain.ITerraMysticaInfo;

public class BoardDTO {

    private TileDTO[] tiles;

    public BoardDTO(ITerraMysticaInfo game) {

        tiles = game.getTileInfo().stream().map(t -> new TileDTO(game, t))
                .toArray(TileDTO[]::new);

    }

    public TileDTO[] getTiles() {
        return tiles;
    }

}
