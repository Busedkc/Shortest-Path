import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CityDistanceCalculator extends JFrame {

    private Graph graph;
    private JTextField startField, endField;
    private JTextArea resultArea;

    public CityDistanceCalculator() {
        graph = loadGraphFromCSV("src//Turkishcities.csv");

        if (graph != null) {
            setTitle("City Distance Calculator");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 300);

            JPanel panel = new JPanel(new GridLayout(4, 2));
            JLabel startLabel = new JLabel("    Start City:");
            startField = new JTextField("   Enter start city");
            JLabel endLabel = new JLabel("    End City:");
            endField = new JTextField("   Enter end city");

            JButton calculateButton = new JButton("Calculate");
            resultArea = new JTextArea();
            resultArea.setEditable(false);

            panel.add(startLabel);
            panel.add(startField);
            panel.add(endLabel);
            panel.add(endField);
            panel.add(new JLabel()); // Empty label for spacing
            panel.add(calculateButton);

            getContentPane().add(BorderLayout.CENTER, panel);

            JPanel resultPanel = new JPanel(new BorderLayout());
            JLabel resultLabel = new JLabel("Results:");
            resultPanel.add(resultLabel, BorderLayout.NORTH);
            resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

            getContentPane().add(BorderLayout.SOUTH, resultPanel);

            // Add Clear button
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

    private void clearFields() {
        startField.setText("");
        endField.setText("");
        resultArea.setText("");
    }

    private void calculateDistance() {
        String startCityName = startField.getText();
        String endCityName = endField.getText();

        City startCity = findCity(startCityName);
        City endCity = findCity(endCityName);

        if (startCity != null && endCity != null) {
            List<City> dfsPath = PathFinder.findShortestPathDFS(graph, startCity, endCity);
            List<City> bfsPath = PathFinder.findShortestPathBFS(graph, startCity, endCity);

            printShortestPath("DFS", dfsPath);
            printShortestPath("BFS", bfsPath);
        } else {
            JOptionPane.showMessageDialog(this, "Cities not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printShortestPath(String algorithm, List<City> path) {
        if (!path.isEmpty()) {
            resultArea.append(algorithm + " Shortest Path: " + path + "\n");
            resultArea.append(algorithm + " Distance: " + calculateTotalDistance(path) + "\n");
        } else {
            resultArea.append(algorithm + " Shortest Path not found.\n");
        }
    }

    private int calculateTotalDistance(List<City> path) {
        int distance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            distance += graph.getDistance(path.get(i), path.get(i + 1));
        }
        return distance;
    }

    private Graph loadGraphFromCSV(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            Graph graph = new Graph();

            String[] cities = scanner.nextLine().split(",");
            if (cities[0].equals("")) {
                cities = Arrays.copyOfRange(cities, 1, cities.length);
            }
            for (String cityName : cities) {
                graph.addCity(new City(cityName));
            }

            int rowIndex = 0;
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
            System.err.println("File not found: " + fileName);
            return null;
        }
    }

    private City findCity(String cityName) {
        for (City city : graph.getCities()) {
            if (city.getName().equalsIgnoreCase(cityName)) {
                return city;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CityDistanceCalculator().setVisible(true);
            }
        });
    }
}
