import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private List<City> cities;
    private Map<City, Map<City, Integer>> distances;

    public Graph() {
        this.cities = new ArrayList<>();
        this.distances = new HashMap<>();
    }

    public void addCity(City city) {
        cities.add(city);
        distances.put(city, new HashMap<>());
    }

    public void addConnection(City city1, City city2, int distance) {
        distances.get(city1).put(city2, distance);
        distances.get(city2).put(city1, distance); // İki yönlü bağlantı ekleyin
    }

    public List<City> getNeighbors(City city) {
        return new ArrayList<>(distances.get(city).keySet());
    }

    public int getDistance(City city1, City city2) {
        return distances.get(city1).getOrDefault(city2, 99999);
    }

    public List<City> getCities() {
        return cities;
    }

    public City getCityAtIndex(int index) {
        return cities.get(index);
    }
}
