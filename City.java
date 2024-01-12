import java.util.HashMap;
import java.util.Map;

//Class representing a City and its distances to other cities
public class City {
    private String name;// Name of the city
    private Map<City, Integer> distances;// Map to store distances to other cities

    // Constructor to initialize a City with a given name
    public City(String name) {
        this.name = name;
        this.distances = new HashMap<>();// Initialize the distances map
    }
    
    // Getter method to retrieve the name of the city
    public String getName() {
        return name;
    }

    // Method to add a distance to another city
    public void addDistanceTo(City otherCity, int distance) {
        distances.put(otherCity, distance);
    }
    
    // Method to get the distance to another city; returns a default value if not found
    public int getDistanceTo(City otherCity) {
        return distances.getOrDefault(otherCity, 99999);
    }
    
    // Override toString method to provide a meaningful representation of the City
    @Override
    public String toString() {
        return name;
    }

    
}
