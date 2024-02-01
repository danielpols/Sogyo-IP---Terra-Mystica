package terra.api.models;

import java.util.Arrays;

import terra.domain.ITerraMystica;

public class BoardDTO {

    private TileDTO[] tiles;

    public BoardDTO(ITerraMystica game) {

        tiles = Arrays.stream(game.getTileLocations())
                .map(loc -> new TileDTO(game, loc)).toArray(TileDTO[]::new);

    }

    public TileDTO[] getTiles() {
        return tiles;
    }

}
