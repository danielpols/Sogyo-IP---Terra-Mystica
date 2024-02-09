package terra.api.models;

import terra.domain.Building;

public class ActionDTO {

    private int[] location;
    private Building building;

    public int[] getLocation() {
        return location;
    }

    public Building getBuilding() {
        return building;
    }

}
