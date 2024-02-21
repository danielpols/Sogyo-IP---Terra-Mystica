package terra.api.models;

import terra.domain.IPlayerInfo;
import terra.domain.ITerraMysticaInfo;
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
    private ActionDTO shippingAction;
    private ActionDTO shovelAction;

    public PlayerDTO(ITerraMysticaInfo game, IPlayerInfo player) {
        this.name = player.getName();
        this.terrain = player.getTerrain();
        this.turn = player.hasTurn();
        this.passed = player.hasPassed();
        this.starting = player.isNewStartingPlayer();

        this.shippingRange = player.getShippingRange();
        this.terraformCost = player.getTerraformCost();
        this.resources = player.getResources();

        this.passAction = ActionDTO.getActionDTO(game.getPassAction(name));
        this.shippingAction = ActionDTO
                .getActionDTO(game.getShippingAction(name));
        this.shovelAction = ActionDTO.getActionDTO(game.getShovelAction(name));
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

    public ActionDTO getShippingAction() {
        return shippingAction;
    }

    public void setShippingAction(ActionDTO shippingAction) {
        this.shippingAction = shippingAction;
    }

    public ActionDTO getShovelAction() {
        return shovelAction;
    }

    public void setShovelAction(ActionDTO shovelAction) {
        this.shovelAction = shovelAction;
    }

}
