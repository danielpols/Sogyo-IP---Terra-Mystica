package terra.api.models;

import terra.domain.ITerraMystica;

public class GameDTO {

    private BoardDTO board;
    private PlayerDTO[] players;
    String message;

    public GameDTO(ITerraMystica game) {
        board = new BoardDTO(game);
        players = game.getPlayerInfo().stream().map(p -> new PlayerDTO(game, p))
                .toArray(PlayerDTO[]::new);
        message = game.getGamePhaseMessage();
    }

    public BoardDTO getBoard() {
        return board;
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public String getMessage() {
        return message;
    }

}
