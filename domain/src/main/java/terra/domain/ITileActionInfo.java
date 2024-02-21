package terra.domain;

interface ITileActionInfo {

    int[] getLocation();

    Terrain getTerrain();

    Building getBuilding();

    boolean hasBuilding();

    boolean isIndirectlyAdjacentTo(Terrain playerTerrain, int shippingRange);

    boolean hasAdjacencyBonus();

}
