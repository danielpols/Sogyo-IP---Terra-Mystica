package terra.api.models;

import terra.domain.ITerraMystica;
import terra.domain.Resource;
import terra.domain.Terrain;

public class PlayerDTO {

    private String name;
    private Terrain terrain;
    private boolean turn;
    private boolean passed;
    private boolean starting;

    private int[] resources;

    private ActionDTO passAction;

    public PlayerDTO(ITerraMystica game, String name) {
        this.name = name;
        this.terrain = game.getPlayerTerrain(name);
        this.turn = game.playerHasTurn(name);
        this.passed = game.playerHasPassed(name);
        this.starting = game.isStartingPlayer(name);

        Resource playerResources = game.getPlayerResource(name);
        this.setResources(new int[] { playerResources.coin(),
                playerResources.worker(), playerResources.priest() });

        this.passAction = ActionDTO.getActionDTO(game.getPassAction(name));
    }

    public String getName() {
        return this.name;
    }

    public Terrain getTerrain() {
        return this.terrain;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public boolean getPassed() {
        return this.passed;
    }

    public boolean getStarting() {
        return this.starting;
    }

    public ActionDTO getPassAction() {
        return this.passAction;
    }

    public int[] getResources() {
        return resources;
    }

    public void setResources(int[] resources) {
        this.resources = resources;
    }

}
