package terra.api.models;

import java.util.Arrays;
import java.util.List;

import terra.domain.Terrain;

public class StartGameDTO {

    private StartPlayerDTO[] players;

    public StartPlayerDTO[] getPlayers() {
        return players;
    }

    public void setPlayers(StartPlayerDTO[] players) {
        this.players = players;
    }

    public List<String> getStartingNames() {
        return Arrays.stream(players).map(p -> p.getName()).toList();
    }

    public List<Terrain> getStartingTerrains() {
        return Arrays.stream(players).map(p -> p.getTerrain()).toList();
    }

}
