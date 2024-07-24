package main.java.utils.io;

import main.java.graph.simple.SimpleGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
    /**
     * Reads an adjacency matrix from a file.
     *
     * @param filename the name of the file containing the adjacency matrix
     * @return a 2D array representing the adjacency matrix, or null if an error occurs
     */
    public static int[][] readAdjacencyMatrixFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            int size = Integer.parseInt(line);
            int[][] adjacencyMatrix = new int[size][size];

            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                for (int j = 0; j < size; j++) {
                    adjacencyMatrix[i][j] = Integer.parseInt(split[j].replaceAll("\\s+", ""));
                }
                i++;
            }

            return adjacencyMatrix;
        } catch (IOException ignored) {
            System.err.println("File '" + filename + "' not found.");
        }

        return null;
    }

    /**
     * Creates a SimpleGraph from an adjacency matrix.
     *
     * @param adjacencyMatrix the adjacency matrix representing the graph
     * @return a SimpleGraph created from the adjacency matrix
     */
    public static SimpleGraph readSimpleGraphFromAdjacencyMatrix(int[][] adjacencyMatrix) {
        int size = adjacencyMatrix.length;
        SimpleGraph graph = new SimpleGraph(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                graph.addEdge(i, j, adjacencyMatrix[i][j]);
            }
        }

        return graph;
    }

    /**
     * Reads a SimpleGraph from a file containing an adjacency matrix.
     *
     * @param filename the name of the file containing the adjacency matrix
     * @return a SimpleGraph created from the file's adjacency matrix, or null if an error occurs
     */
    public static SimpleGraph readSimpleGraphFromFile(String filename) {
        int[][] adjacencyMatrix = GraphReader.readAdjacencyMatrixFromFile(filename);

        if (adjacencyMatrix == null) {
            return null;
        }

        return readSimpleGraphFromAdjacencyMatrix(adjacencyMatrix);
    }
}