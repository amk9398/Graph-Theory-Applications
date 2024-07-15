package main.java.graph;

public class GraphUtils {
    public static SimpleGraph readSimpleGraphFromAdjacencyMatrix(int[][] adjacencyMatrix) {
        int size = adjacencyMatrix[0].length;

        SimpleGraph graph = new SimpleGraph(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graph.addEdge(i, j, adjacencyMatrix[i][j]);
            }
        }

        return graph;
    }
}