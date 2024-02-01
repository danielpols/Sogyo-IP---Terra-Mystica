package terra.api.models;

import terra.domain.ITerraMystica;

public class GameDTO {

    private BoardDTO board;

    public GameDTO(ITerraMystica game) {
        board = new BoardDTO(game);
    }

    public BoardDTO getBoard() {
        return board;
    }

}
