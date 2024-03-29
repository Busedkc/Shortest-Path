import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//Class representing a JFrame application for calculating city distances
public class CityDistanceCalculator extends JFrame {

    private Graph graph;// Graph to store city connections and distances
    private JTextField startField, endField;// Text fields for user input
    private JTextArea resultArea;// Text area to display calculation results

    // Constructor to initialize the CityDistanceCalculator
    public CityDistanceCalculator() {
    	// Load graph data from CSV file
        graph = loadGraphFromCSV("src//Turkishcities.csv");

        // Check if the graph is successfully loaded
        if (graph != null) {
            setTitle("City Distance Calculator");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 300);

            // Create a panel with a grid layout to organize components
            JPanel panel = new JPanel(new GridLayout(4, 2));
            JLabel startLabel = new JLabel("    Start City:");
            startField = new JTextField("   Enter start city");
            JLabel endLabel = new JLabel("    End City:");
            endField = new JTextField("   Enter end city");

            // Create Calculate button and result area
            JButton calculateButton = new JButton("Calculate");
            resultArea = new JTextArea();
            resultArea.setEditable(false);

            // Add components to the panel
            panel.add(startLabel);
            panel.add(startField);
            panel.add(endLabel);
            panel.add(endField);
            panel.add(new JLabel()); // Empty label for spacing
            panel.add(calculateButton);

            // Add panel to the center of the frame
            getContentPane().add(BorderLayout.CENTER, panel);
            
            // Create a result panel with a border layout
            JPanel resultPanel = new JPanel(new BorderLayout());
            JLabel resultLabel = new JLabel("Results:");
            resultPanel.add(resultLabel, BorderLayout.NORTH);
            resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

            // Add result panel to the bottom of the frame
            getContentPane().add(BorderLayout.SOUTH, resultPanel);

            // Add Clear button with an ActionListener to clear input fields
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearFields();
                }
            });
            panel.add(clearButton);

            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    calculateDistance();
                }
            });
        }
    }
    
    // Method to clear input fields and result area
    private void clearFields() {
        startField.setText("");
        endField.setText("");
        resultArea.setText("");
    }
    
    // Method to calculate and display distances between start and end cities
    private void calculateDistance() {
        String startCityName = startField.getText();
        String endCityName = endField.getText();
        
        // Find City objects for the given city names
        City startCity = findCity(startCityName);
        City endCity = findCity(endCityName);

        // Check if both cities are found
        if (startCity != null && endCity != null) {
        	// Find shortest paths using DFS and BFS algorithms
            List<City> dfsPath = PathFinder.findShortestPathDFS(graph, startCity, endCity);
            List<City> bfsPath = PathFinder.findShortestPathBFS(graph, startCity, endCity);

            // Print results for DFS and BFS paths
            printShortestPath("DFS", dfsPath);
            printShortestPath("BFS", bfsPath);
        } else {
        	// Display an error message if cities are not found
            JOptionPane.showMessageDialog(this, "Cities not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to print the shortest path and distance for a given algorithm
    private void printShortestPath(String algorithm, List<City> path) {
        if (!path.isEmpty()) {
            resultArea.append(algorithm + " Shortest Path: " + path + "\n");
            resultArea.append(algorithm + " Distance: " + calculateTotalDistance(path) + "\n");
        } else {
            resultArea.append(algorithm + " Shortest Path not found.\n");
        }
    }

    // Method to calculate the total distance for a given path
    private int calculateTotalDistance(List<City> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += graph.getDistance(path.get(i), path.get(i + 1));
        }
        return distance;
    }
  
    // Method to load a graph from a CSV file
    private Graph loadGraphFromCSV(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            Graph graph = new Graph();

            // Read the first line to get city names
            String[] cities = scanner.nextLine().split(",");
            if (cities[0].equals("")) {
                cities = Arrays.copyOfRange(cities, 1, cities.length);
            }
            
            // Add cities to the graph
            for (String cityName : cities) {
                graph.addCity(new City(cityName));
            }

            int rowIndex = 0;
            // Read the remaining lines to get distances and build connections
            while (scanner.hasNextLine()) {
                String[] distances = scanner.nextLine().split(",");
                distances = Arrays.copyOfRange(distances, 1, distances.length);

                City currentCity = graph.getCityAtIndex(rowIndex);

                for (int i = 0; i < distances.length - 1; i++) {
                    if (!distances[i].equals("99999") && !distances[i].equals("0")) {
                        try {
                            City connectedCity = graph.getCityAtIndex(i);
                            int distance = Integer.parseInt(distances[i]);
                            graph.addConnection(currentCity, connectedCity, distance);
                        } catch (NumberFormatException ex) {
                            // Handle the exception if necessary
                        }
                    }
                }
                rowIndex++;
            }

            scanner.close();
            return graph;
        } catch (FileNotFoundException e) {
        	 // Print an error message if the file is not found
        	System.err.println("File not found: " + fileName);
            return null;
        }
    }
  
    // Method to find a City object by name in the loaded graph
    private City findCity(String cityName) {
        for (City city : graph.getCities()) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        return null;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CityDistanceCalculator().setVisible(true);
            }
        });
    }
}
