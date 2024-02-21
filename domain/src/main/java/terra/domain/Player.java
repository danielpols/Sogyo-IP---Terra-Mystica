package terra.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import terra.domain.actions.GameAction;
import terra.domain.actions.PassAction;
import terra.domain.actions.ShippingAction;
import terra.domain.actions.ShovelAction;
import terra.domain.actions.TileAction;
import terra.domain.actions.UpgradeAction;

public class Player implements IPlayerInfo, IPlayerActionInfo {

    private final String name;
    private final Terrain terrain;
    private boolean turn;
    private boolean passed = false;
    private boolean startPlayer = false;
    private final Player nextPlayer;

    private HashMap<Building, Integer> amountBuilt = new HashMap<Building, Integer>();
    private HashMap<Building, List<Resource>> rewards = new HashMap<Building, List<Resource>>();

    private int shippingRange = 0;
    private int maxRange = 3;
    private Resource rangeCost = new Resource(4, 0, 1);

    private Resource[] terraformCost = { new Resource(0, 3, 0),
            new Resource(0, 2, 0), new Resource(0, 1, 0) };
    private int terraformStep = 0;
    private Resource tfImproveCost = new Resource(5, 2, 1);

    private Resource resource = new Resource(15, 3, 0);

    public Player(List<String> names, List<Terrain> terrains) {
        this.name = names.get(0);
        this.terrain = terrains.get(0);
        this.turn = true;
        this.nextPlayer = new Player(names.subList(1, names.size()),
                terrains.subList(1, terrains.size()), this);
        defineBuildings();
    }

    public Player(List<String> names, List<Terrain> terrains, Player player) {
        this.name = names.get(0);
        this.terrain = terrains.get(0);
        this.turn = false;
        if (names.size() == 1) {
            nextPlayer = player;
        } else {
            nextPlayer = new Player(names.subList(1, names.size()),
                    terrains.subList(1, terrains.size()), player);
        }
        defineBuildings();
    }

    private void defineBuildings() {
        Arrays.stream(Building.values()).filter(b -> !b.equals(Building.NONE))
                .forEach(b -> amountBuilt.put(b, 0));
        rewards.put(Building.DWELLING,
                Arrays.asList(IntStream.range(0, 8)
                        .mapToObj(i -> i == 7 ? new Resource(0, 0, 0)
                                : new Resource(0, 1, 0))
                        .toArray(Resource[]::new)));
        rewards.put(Building.TRADING,
                Arrays.asList(IntStream.range(0, 4)
                        .mapToObj(i -> i < 2 ? new Resource(2, 0, 0)
                                : new Resource(2, 0, 0))
                        .toArray(Resource[]::new)));
        rewards.put(Building.FORTRESS, Arrays.asList(new Resource(0, 0, 1)));
        rewards.put(Building.CHURCH,
                Arrays.asList(IntStream.range(0, 3)
                        .mapToObj(i -> new Resource(0, 0, 1))
                        .toArray(Resource[]::new)));
        rewards.put(Building.SANCTUARY, Arrays.asList(new Resource(0, 0, 1)));
    }

    protected List<Player> getAllPlayers() {
        List<Player> list = new ArrayList<Player>();
        getAllPlayers(list);
        return list;
    }

    private void getAllPlayers(List<Player> list) {
        if (!list.contains(this)) {
            list.add(this);
            nextPlayer.getAllPlayers(list);
        }
    }

    public String getName() {
        return name;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public boolean hasTurn() {
        return turn;
    }

    public boolean hasPassed() {
        return passed;
    }

    public boolean isNewStartingPlayer() {
        return startPlayer;
    }

    public int getShippingRange() {
        return shippingRange;
    }

    public int[] getTerraformCost() {
        return terraformCost[terraformStep].toArray();
    }

    public int[] getResources() {
        return resource.toArray();
    }

    public int[] getIncome() {
        return getIncomeResource().toArray();
    }

    protected void gainIncome() {
        resource = resource.add(getIncomeResource());
    }

    private Resource getIncomeResource() {
        Resource income = new Resource(0, 1, 0); // default
        income = income
                .addAll(amountBuilt.keySet().stream()
                        .flatMap(k -> IntStream.range(0, amountBuilt.get(k))
                                .mapToObj(i -> rewards.get(k).get(i)))
                        .toList());
        return income;
    }

    protected Resource getBuildingCost(String name, Building building,
            boolean adjacent) {
        return getPlayer(name).getBuildingCost(building, adjacent);
    }

    public Resource getBuildingCost(Building building, boolean adjacent) {
        switch (building) {
        case CHURCH:
            return new Resource(5, 2, 0);
        case DWELLING:
            return new Resource(2, 1, 0);
        case FORTRESS:
            return new Resource(6, 4, 0);
        case SANCTUARY:
            return new Resource(6, 4, 0);
        case TRADING:
            return new Resource(adjacent ? 3 : 6, 2, 0);
        default:
            break;

        }
        return new Resource(0, 0, 0);
    }

    public Resource getTerraformCost(Terrain origin) {
        return terraformCost[terraformStep]
                .multiply(terrain.distanceTo(origin).getAsInt());
    }

    public Resource getShippingImprovementCost() {
        if (shippingRange < maxRange) {
            return rangeCost;
        }
        return null;
    }

    public Resource getShovelImprovementCost() {
        if (terraformStep < terraformCost.length - 1) {
            return tfImproveCost;
        }
        return null;
    }

    public boolean canPayCost(Resource cost) {
        return resource.coin() >= cost.coin()
                && resource.worker() >= cost.worker()
                && resource.priest() >= cost.priest();
    }

    public boolean canBuildBuilding(Building building) {
        return amountBuilt.get(building) < rewards.get(building).size();
    }

    protected Player getPlayer(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        return nextPlayer.getPlayer(name);
    }

    protected Player getTurnPlayer() {
        if (turn) {
            return this;
        }
        return nextPlayer.getTurnPlayer();
    }

    protected void perform(GameAction action) {
        Player targetPlayer = getPlayer(action.getPlayerName());
        if (action instanceof TileAction) {
            TileAction tileAction = (TileAction) action;
            targetPlayer.buildBuilding(tileAction instanceof UpgradeAction
                    ? ((UpgradeAction) tileAction).getSourceBuilding()
                    : null, tileAction.getTargetBuilding());
            targetPlayer.payForCost(action.getCost());
        }
        if (action instanceof PassAction) {
            targetPlayer.pass();
        }
        if (action instanceof ShippingAction) {
            targetPlayer.shippingRange++;
            targetPlayer.payForCost(action.getCost());
        }
        if (action instanceof ShovelAction) {
            targetPlayer.terraformStep++;
            targetPlayer.payForCost(action.getCost());
        }
    }

    private void buildBuilding(Building source, Building target) {
        if (source != null) {
            amountBuilt.put(source, amountBuilt.get(source) - 1);
        }
        amountBuilt.put(target, amountBuilt.get(target) + 1);
    }

    private void payForCost(Resource cost) {
        resource = resource.subtract(cost);
    }

    private boolean noneHavePassed() {
        if (passed) {
            return false;
        }
        if (turn) {
            return true;
        }
        return nextPlayer.noneHavePassed();
    }

    private boolean allHavePassed() {
        if (!passed) {
            return false;
        }
        if (turn && passed) {
            return true;
        }
        return nextPlayer.allHavePassed();
    }

    private boolean firstToPass() {
        return getTurnPlayer().nextPlayer.noneHavePassed();
    }

    private boolean lastToPass() {
        return getTurnPlayer().nextPlayer.allHavePassed();
    }

    private void pass() {
        if (turn) {
            if (firstToPass()) {
                startPlayer = true;
            }
            passed = true;
            if (!lastToPass()) {
                switchTurn();
            } else {
                turn = false;
            }
        }
    }

    protected void endTurn(String name) {
        if (getPlayer(name).hasTurn()) {
            getPlayer(name).switchTurn();
        }
    }

    private void switchTurn() {
        if (turn) {
            turn = false;
            nextPlayer.switchTurn();
        } else if (passed) {
            nextPlayer.switchTurn();
        } else {
            turn = true;
        }
    }

    protected void endTurnReverse(String name) {
        if (getPlayer(name).hasTurn()) {
            getPlayer(name).switchTurnReverse();
        }
    }

    private Player findPreviousPlayer(String playerName) {
        if (nextPlayer.name.equals(playerName)) {
            return this;
        }
        return nextPlayer.findPreviousPlayer(playerName);
    }

    private void switchTurnReverse() {
        if (turn) {
            turn = false;
            findPreviousPlayer(name).switchTurnReverse();
        } else if (passed) {
            findPreviousPlayer(name).switchTurnReverse();
        } else {
            turn = true;
        }
    }

    protected void startNewRound() {
        if (!passed) {
            return;
        }
        passed = false;
        if (startPlayer) {
            turn = true;
        }
        startPlayer = false;
        nextPlayer.startNewRound();
    }

}
