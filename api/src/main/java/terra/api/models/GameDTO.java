package terra.api.models;

import java.util.Arrays;

import terra.domain.ITerraMystica;

public class GameDTO {

    private BoardDTO board;
    private PlayerDTO[] players;

    public GameDTO(ITerraMystica game) {
        board = new BoardDTO(game);
        players = Arrays.stream(game.getPlayers()).map(p -> new PlayerDTO(p))
                .toArray(PlayerDTO[]::new);
    }

    public BoardDTO getBoard() {
        return board;
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

}
