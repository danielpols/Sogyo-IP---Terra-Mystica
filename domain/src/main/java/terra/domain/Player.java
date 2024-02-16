package terra.domain;

import java.util.ArrayList;
import java.util.List;

import terra.domain.actions.PassAction;
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

    public int getPlayerShippingRange(String name) {
        return findPlayer(name).shippingRange;
    }

    private Player findPlayer(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return nextPlayer.findPlayer(name);
    }

    protected void perform(PlayerAction action) {
        if (action instanceof PassAction) {
            findPlayer(action.getPlayerName()).pass();
        }
    }

    private Player getTurnPlayer() {
        if (turn) {
            return this;
        }
        return nextPlayer.getTurnPlayer();
    }

    private boolean noneHavePassed() {
        if (passed) {
            return false;
        }
        if (turn) {
            return true;
        }
        return nextPlayer.noneHavePassed();
    }

    private boolean allHavePassed() {
        if (!passed) {
            return false;
        }
        if (turn && passed) {
            return true;
        }
        return nextPlayer.allHavePassed();
    }

    private boolean firstToPass() {
        return getTurnPlayer().nextPlayer.noneHavePassed();
    }

    private boolean lastToPass() {
        return getTurnPlayer().nextPlayer.allHavePassed();
    }

    private void pass() {
        if (turn) {
            if (firstToPass()) {
                startPlayer = true;
            }
            passed = true;
            if (!lastToPass()) {
                switchTurn();
            } else {
                turn = false;
            }
        }
    }

    protected void endTurn(String name) {
        if (playerHasTurn(name)) {
            findPlayer(name).switchTurn();
        }
    }

    private void switchTurn() {
        if (turn) {
            turn = !turn;
            nextPlayer.switchTurn();
        } else if (passed) {
            nextPlayer.switchTurn();
        } else {
            turn = !turn;
        }
    }

    protected void endTurnReverse(String name) {
        if (playerHasTurn(name)) {
            findPlayer(name).switchTurnReverse();
        }
    }

    private Player findPreviousPlayer(String playerName) {
        if (nextPlayer.name.equals(playerName)) {
            return this;
        }
        return nextPlayer.findPreviousPlayer(playerName);
    }

    private void switchTurnReverse() {
        if (turn) {
            turn = !turn;
            findPreviousPlayer(name).switchTurnReverse();
        } else if (passed) {
            findPreviousPlayer(name).switchTurnReverse();
        } else {
            turn = !turn;
        }
    }

    protected void startNewRound() {
        if (!passed) {
            return;
        }
        passed = false;
        if (startPlayer) {
            turn = true;
        }
        startPlayer = false;
        nextPlayer.startNewRound();
    }

}
