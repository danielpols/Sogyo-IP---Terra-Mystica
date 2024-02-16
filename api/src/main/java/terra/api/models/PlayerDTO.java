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

    private int shippingRange;
    private int[] terraformCost;

    private int[] resources;

    private ActionDTO passAction;

    public PlayerDTO(ITerraMystica game, String name) {
        this.name = name;
        this.terrain = game.getPlayerTerrain(name);
        this.turn = game.playerHasTurn(name);
        this.passed = game.playerHasPassed(name);
        this.starting = game.isStartingPlayer(name);

        this.setShippingRange(game.getPlayerShippingRange(name));

        Resource tCost = game.getPlayerTerraformCost(name, 1);
        this.terraformCost = new int[] { tCost.coin(), tCost.worker(),
                tCost.priest() };

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

    public int getShippingRange() {
        return shippingRange;
    }

    public void setShippingRange(int shippingRange) {
        this.shippingRange = shippingRange;
    }

    public int[] getTerraformCost() {
        return terraformCost;
    }

    public void setTerraformCost(int[] terraformCost) {
        this.terraformCost = terraformCost;
    }

}
