package terra.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Building {

    NONE, DWELLING, TRADING, FORTRESS, CHURCH, SANCTUARY;

    public Set<Building> upgrades() {
        switch (this) {
        case CHURCH:
            return Stream.of(SANCTUARY).collect(Collectors.toSet());
        case DWELLING:
            return Stream.of(TRADING).collect(Collectors.toSet());
        case FORTRESS:
            break;
        case NONE:
            return Stream.of(DWELLING).collect(Collectors.toSet());
        case SANCTUARY:
            break;
        case TRADING:
            return Stream.of(FORTRESS, CHURCH).collect(Collectors.toSet());
        default:
            break;
        }
        return new HashSet<Building>();
    }

    public boolean upgradesTo(Building other) {
        return upgrades().contains(other);
    }

}
