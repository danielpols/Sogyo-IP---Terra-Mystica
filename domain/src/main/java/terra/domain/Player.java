package terra.domain;

import java.util.ArrayList;
import java.util.List;

import terra.domain.actions.PlayerAction;

public class Player {

    private final String name;
    private final Terrain terrain;
    private boolean turn;
    private boolean passed = false;
    private boolean startPlayer = false;
    private final Player nextPlayer;

    private int shippingRange = 1;

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

    public List<String> getAllPlayerNames() {
        ArrayList<String> list = new ArrayList<String>();
        getAllPlayerNames(list);
        return list;
    }

    public void getAllPlayerNames(List<String> list) {
        if (!list.contains(name)) {
            list.add(name);
            nextPlayer.getAllPlayerNames(list);
        }
    }

    public Terrain getPlayerTerrain(String name) {
        return findPlayer(name).terrain;
    }

    public boolean playerHasTurn(String name) {
        return findPlayer(name).turn;
    }

    public boolean playerHasPassed(String name) {
        return findPlayer(name).passed;
    }

    public boolean isStartingPlayer(String name) {
        return findPlayer(name).startPlayer;
    }

    private Player findPlayer(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return nextPlayer.findPlayer(name);
    }

    protected void perform(PlayerAction action) {

    }

    public Player getTurnPlayer() {
        if (turn) {
            return this;
        }
        return nextPlayer.getTurnPlayer();
    }

    public void passTurn() {
        if (turn) {
            switchTurn();
        }
    }

    private void switchTurn() {
        if (turn) {
            nextPlayer.switchTurn();
        }
        turn = !turn;
    }

    public Player getNextPlayer() {
        return nextPlayer;
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

    public int getShippingRange() {
        return shippingRange;
    }

}
