package main.java.graph;

public class GraphUtils {
    public static Graph readAdjacencyMatrix(int[][] adjacencyMatrix) {
        int size = adjacencyMatrix[0].length;

        Graph graph = new Graph(size);
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                graph.setWeight(i, j, adjacencyMatrix[i][j]);
            }
        }

        return graph;
    }
}