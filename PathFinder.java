import java.util.*;

public class PathFinder {
    // Method to find the shortest path between two cities using Depth-First Search (DFS)
    public static List<City> findShortestPathDFS(Graph graph, City start, City end) {
        Stack<List<City>> stack = new Stack<>(); // Stack to perform DFS
        Set<City> visited = new HashSet<>(); // Set to keep track of visited cities

        List<City> initialPath = new ArrayList<>();
        initialPath.add(start);
        stack.push(initialPath);
        visited.add(start);

        while (!stack.isEmpty()) {
            List<City> currentPath = stack.pop();
            City current = currentPath.get(currentPath.size() - 1);

            if (current.equals(end)) {
                return currentPath; // Return the path if the destination is reached
            }

            for (City neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    List<City> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    stack.push(newPath);
                    visited.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // Shortest path not found.
    }

    // Method to find the shortest path between two cities using Breadth-First Search (BFS)
    public static List<City> findShortestPathBFS(Graph graph, City start, City end) {
        Queue<List<City>> queue = new LinkedList<>(); // Queue to perform BFS
        Set<City> visited = new HashSet<>(); // Set to keep track of visited cities

        List<City> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<City> currentPath = queue.poll();
            City current = currentPath.get(currentPath.size() - 1);

            if (current.equals(end)) {
                return currentPath; // Return the path if the destination is reached
            }

            for (City neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    List<City> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    queue.add(newPath);
                    visited.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // Shortest path not found.
    }
}
