package terra.domain;

import java.util.List;

public record Resource(int coin, int worker, int priest) {
    public Resource add(Resource other) {
        return new Resource(coin + other.coin, worker + other.worker,
                priest + other.priest);
    }

    public Resource subtract(Resource other) {
        return new Resource(coin - other.coin, worker - other.worker,
                priest - other.priest);
    }

    public Resource addAll(List<Resource> list) {
        return add(sum(list));
    }

    private static Resource sum(List<Resource> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        return list.get(0).add(Resource.sum(list.subList(1, list.size())));
    }
}
