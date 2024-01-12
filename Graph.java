import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Class representing a graph of cities and their distances
public class Graph {
    private List<City> cities;
    private Map<City, Map<City, Integer>> distances;

    // Constructor to initialize the graph with empty city list and distance map
    public Graph() {
        this.cities = new ArrayList<>();
        this.distances = new HashMap<>();
    }

    // Method to add a city to the graph
    public void addCity(City city) {
        cities.add(city);
        distances.put(city, new HashMap<>());
    }

    // Method to add a two-way connection between two cities with a given distance
    public void addConnection(City city1, City city2, int distance) {
        distances.get(city1).put(city2, distance);
        distances.get(city2).put(city1, distance); // add two-way connections with distances
    }

    // Method to get a list of neighboring cities for a given city
    public List<City> getNeighbors(City city) {
        return new ArrayList<>(distances.get(city).keySet());
    }

    // Method to get the distance between two cities
    public int getDistance(City city1, City city2) {
        return distances.get(city1).getOrDefault(city2, 99999);
    }

    // Method to retrieve the list of cities in the graph
    public List<City> getCities() {
        return cities;
    }
     
    // Method to get a city at a specified index in the list
    public City getCityAtIndex(int index) {
        return cities.get(index);
    }
}
