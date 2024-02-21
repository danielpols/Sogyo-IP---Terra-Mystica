package terra.domain;

public interface IPlayerInfo {

    String getName();

    Terrain getTerrain();

    boolean hasTurn();

    boolean hasPassed();

    boolean isNewStartingPlayer();

    int getShippingRange();

    int[] getTerraformCost();

    int[] getResources();

    int[] getIncome();

}
