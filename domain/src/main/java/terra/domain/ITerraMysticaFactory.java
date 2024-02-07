package terra.domain;

import java.util.List;

public interface ITerraMysticaFactory {

    ITerraMystica startGame(List<String> names, List<Terrain> playerTerrains,
            Terrain[] terrains);

}
