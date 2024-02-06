package terra.domain;

public class Player {

    private String name;
    private Terrain terrain;
    private boolean turn;

    public Player(String name, Terrain terrain) {
        this.name = name;
        this.terrain = terrain;
        this.turn = false;
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
