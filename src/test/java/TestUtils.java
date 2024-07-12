package test.java;

import main.java.graph.Graph;
import main.java.graph.GraphUtils;
import main.java.utils.io.GraphReader;

import java.util.HashSet;

public class TestUtils {
    private static final HashSet<String> testFiles = new HashSet<>();

    static {
        testFiles.add("src/data/simpleGraph.txt");
        testFiles.add("src/data/mediumGraph.txt");
        testFiles.add("src/data/largeGraph.txt");
        testFiles.add("src/data/sparseGraph.txt");
        testFiles.add("src/data/connectedGraph.txt");
    }

    public static HashSet<Graph> getTestGraphs() {
        HashSet<Graph> testGraphs = new HashSet<>();
        for (String filename : testFiles) {
            int[][] adjacencyMatrix = GraphReader.readAdjacencyMatrixFromFile(filename);
            Graph graph = GraphUtils.readAdjacencyMatrix(adjacencyMatrix);
            testGraphs.add(graph);
        }

        return testGraphs;
    }
}
