package terra.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import terra.domain.Terrain;
import terra.domain.actions.BuildAction;
import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.UpgradeAction;

@Entity
public class PersistableGameObject {
    @Id
    private String id;
    @Embedded
    private List<String> nameList;
    @Embedded
    private List<Terrain> terrainList;
    @Embedded
    private List<PersistableGameAction> actionList;

    public PersistableGameObject(String id, List<String> names,
            List<Terrain> terrains) {
        this.id = id;
        this.nameList = names;
        this.terrainList = terrains;
        this.actionList = new ArrayList<PersistableGameAction>();
    }

    public void addAction(GameAction action) {
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

    public List<String> getNameList() {
        return nameList;
    }

    public List<Terrain> getTerrainList() {
        return terrainList;
    }

    public List<PersistableGameAction> getActionList() {
        return actionList;
    }

}