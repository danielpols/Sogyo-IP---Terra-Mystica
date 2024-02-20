package terra.domain;

interface IPlayerActionInfo {

    String getName();

    Terrain getTerrain();

    int getShippingRange();

    Resource getShippingImprovementCost();

    Resource getShovelImprovementCost();

    Resource getBuildingCost(Building building, boolean adjacent);

    Resource getTerraformCost(Terrain origin);

    boolean canPayCost(Resource cost);

    boolean canBuildBuilding(Building building);
}
