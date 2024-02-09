package terra.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BuildingTest {

    @Test
    public void testUpgradeTree() {
        assertTrue(Building.NONE.upgradesTo(Building.DWELLING));
        assertTrue(Building.DWELLING.upgradesTo(Building.TRADING));
        assertTrue(Building.TRADING.upgradesTo(Building.FORTRESS));
        assertTrue(Building.TRADING.upgradesTo(Building.CHURCH));
        assertTrue(Building.CHURCH.upgradesTo(Building.SANCTUARY));
    }

    @Test
    public void testNothingUpgradesToNone() {
        assertAll(Arrays.stream(Building.values())
                .map(b -> (() -> assertFalse(b.upgradesTo(Building.NONE)))));
    }

    @Test
    public void testFortressAndSanctuaryUpgradeToNothing() {
        assertAll(Arrays.stream(Building.values()).map(
                b -> (() -> assertFalse(Building.FORTRESS.upgradesTo(b)))));
        assertAll(Arrays.stream(Building.values()).map(
                b -> (() -> assertFalse(Building.SANCTUARY.upgradesTo(b)))));
    }

}
