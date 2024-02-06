package terra.domain;

public class Player {

    private String name;
    private Terrain terrain;

    public Player(String name, Terrain terrain) {
        this.name = name;
        this.terrain = terrain;
    }

    public String getName() {
        return name;
    }

    public Terrain getTerrain() {
        return terrain;
    }

}
