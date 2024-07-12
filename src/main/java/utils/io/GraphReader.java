package main.java.utils.io;

import main.java.graph.Graph;
import main.java.graph.GraphUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {
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

    public static Graph readGraphFromFile(String filename) {
        int[][] adjacencyMatrix = GraphReader.readAdjacencyMatrixFromFile(filename);
        if (adjacencyMatrix == null) {
            return null;
        }

        return GraphUtils.readAdjacencyMatrix(adjacencyMatrix);
    }
}