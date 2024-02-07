package terra.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final Terrain terrain;
    private boolean turn;
    private final Player nextPlayer;

    public Player(String name, Terrain terrain) {
        this.name = name;
        this.terrain = terrain;
        this.turn = false;
        this.nextPlayer = null;
    }

    public Player(List<String> names, List<Terrain> terrains) {
        this.name = names.get(0);
        this.terrain = terrains.get(0);
        this.turn = true;
        this.nextPlayer = new Player(names.subList(1, names.size()),
                terrains.subList(1, terrains.size()), this);
    }

    public Player(List<String> names, List<Terrain> terrains, Player player) {
        this.name = names.get(0);
        this.terrain = terrains.get(0);
        this.turn = false;
        if (names.size() == 1) {
            nextPlayer = player;
        } else {
            nextPlayer = new Player(names.subList(1, names.size()),
                    terrains.subList(1, terrains.size()), player);
        }
    }

    public List<Player> getAllPlayers() {
        ArrayList<Player> list = new ArrayList<Player>();
        getAllPlayers(list);
        return list;
    }

    public void getAllPlayers(List<Player> list) {
        if (!list.contains(this)) {
            list.add(this);
            nextPlayer.getAllPlayers(list);
        }
    }

    public Player getTurnPlayer() {
        if (turn) {
            return this;
        }
        return nextPlayer.getTurnPlayer();
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void switchTurn() {
        turn = !turn;
    }

    public String getName() {
        return name;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean hasTurn() {
        return turn;
    }

}
