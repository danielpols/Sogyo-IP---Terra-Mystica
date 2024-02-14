package terra.api.models;

import terra.domain.ITerraMystica;

public class GameDTO {

    private BoardDTO board;
    private PlayerDTO[] players;

    public GameDTO(ITerraMystica game) {
        board = new BoardDTO(game);
        players = game.getPlayerNames().stream()
                .map(name -> new PlayerDTO(game, name))
                .toArray(PlayerDTO[]::new);
    }

    public BoardDTO getBoard() {
        return board;
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

}
