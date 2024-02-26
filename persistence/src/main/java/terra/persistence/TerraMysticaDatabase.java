package terra.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;

import terra.domain.Building;
import terra.domain.Resource;
import terra.domain.Terrain;
import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.UpgradeAction;

public class TerraMysticaDatabase implements ITerraMysticaDatabase {

    private static String start = "PMFLDWPSWFLWS" + "DRRPSRRDSRRD"
            + "RRSRMRFRFRMRR" + "FLDRRWLRWRWP" + "SPWLSPMDRRFSL"
            + "MFRRDFRRRPMP" + "RRRMRWRFRDSLD" + "DLPRRRLSRMPM"
            + "WSMLWFDPMRLFW";

    EntityManager manager;

    public TerraMysticaDatabase() {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("terramystica.odb");
        this.manager = emf.createEntityManager();
    }

    public Terrain[] getStartingBoard() {
        return start.chars().mapToObj(c -> Terrain.getTerrain((char) c))
                .toArray(Terrain[]::new);
    }

    public boolean hasID(String id) {
        return false;
    }

    public void initialiseGame(String id, List<String> playerNames,
            List<Terrain> playerTerrains) {

    }

    public List<String> getPlayerNames(String id) {
        return Arrays.asList("Henk", "Jaap");
    }

    public List<Terrain> getPlayerTerrains(String id) {
        return Arrays.asList(Terrain.FOREST, Terrain.SWAMP);
    }

    public List<GameAction> getGameActions(String id) {
        return new ArrayList<GameAction>();
    }

    public void saveAction(String id, GameAction action) {

    }

}

@Entity
class GameObject {
    @Id
    private String id;
    @Embedded
    private List<String> nameList;
    @Embedded
    private List<Terrain> terrainList;
    @Embedded
    private List<PersistableGameAction> actionList;

    GameObject(String id, List<String> names, List<Terrain> terrains) {
        this.id = id;
        this.nameList = names;
        this.terrainList = terrains;
        this.actionList = new ArrayList<PersistableGameAction>();
    }

    void addAction(GameAction action) {
        if (action instanceof BuildAction) {
            actionList.add(new PersistableBuildAction((BuildAction) action));
        }
        if (action instanceof UpgradeAction) {
            actionList
                    .add(new PersistableUpgradeAction((UpgradeAction) action));
        }
        if (action instanceof PassAction) {
            actionList.add(new PersistablePassAction((PassAction) action));
        }
        if (action instanceof ShippingAction) {
            actionList.add(
                    new PersistableShippingAction((ShippingAction) action));
        }
        if (action instanceof ShovelAction) {
            actionList.add(new PersistableShovelAction((ShovelAction) action));
        }
    }

    List<String> getNameList() {
        return nameList;
    }

    List<Terrain> getTerrainList() {
        return terrainList;
    }

    List<PersistableGameAction> getActionList() {
        return actionList;
    }

}

@Embeddable
abstract class PersistableGameAction {

    String playerName;
    int[] cost;

    PersistableGameAction(GameAction action) {
        this.playerName = action.getPlayerName();
        this.cost = action.getCost().toArray();
    }

    protected Resource getResourceCost() {
        return new Resource(cost[0], cost[1], cost[2]);
    }

    abstract GameAction toAction();

}

@Embeddable
class PersistableBuildAction extends PersistableGameAction {

    int[] location;
    Terrain playerTerrain;
    Building targetBuilding;
    int terraformCost;

    PersistableBuildAction(BuildAction action) {
        super(action);
        this.location = action.getLocation();
        this.playerTerrain = action.getPlayerTerrain();
        this.targetBuilding = action.getTargetBuilding();
        this.terraformCost = action.getTerraformCost();
    }

    GameAction toAction() {
        return new BuildAction(playerName, getResourceCost(), location,
                playerTerrain, targetBuilding, terraformCost);
    }

}

@Embeddable
class PersistableUpgradeAction extends PersistableGameAction {

    int[] location;
    Building sourceBuilding;
    Building targetBuilding;

    PersistableUpgradeAction(UpgradeAction action) {
        super(action);
        this.location = action.getLocation();
        this.sourceBuilding = action.getSourceBuilding();
        this.targetBuilding = action.getTargetBuilding();
    }

    GameAction toAction() {
        return new UpgradeAction(playerName, getResourceCost(), location,
                sourceBuilding, targetBuilding);
    }

}

@Embeddable
class PersistablePassAction extends PersistableGameAction {

    boolean starting;

    PersistablePassAction(PassAction action) {
        super(action);
        this.starting = action.isStarting();
    }

    GameAction toAction() {
        return new PassAction(playerName, starting);
    }
}

@Embeddable
class PersistableShippingAction extends PersistableGameAction {

    int newRange;

    PersistableShippingAction(ShippingAction action) {
        super(action);
        this.newRange = action.getNewRange();
    }

    GameAction toAction() {
        return new ShippingAction(playerName, getResourceCost(), newRange);
    }

}

@Embeddable
class PersistableShovelAction extends PersistableGameAction {

    int[] newCost;

    public PersistableShovelAction(ShovelAction action) {
        super(action);
        this.newCost = action.getNewCost().toArray();
    }

    GameAction toAction() {
        return new ShovelAction(playerName, getResourceCost(),
                new Resource(newCost[0], newCost[1], newCost[2]));
    }

}
