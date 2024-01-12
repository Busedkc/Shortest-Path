import java.util.HashMap;
import java.util.Map;

public class City {
    private String name;
    private Map<City, Integer> distances;

    public City(String name) {
        this.name = name;
        this.distances = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addDistanceTo(City otherCity, int distance) {
        distances.put(otherCity, distance);
    }

    public int getDistanceTo(City otherCity) {
        return distances.getOrDefault(otherCity, 99999);
    }

    @Override
    public String toString() {
        return name;
    }

    // Diğer sınıf metotları...
}
