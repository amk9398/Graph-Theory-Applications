package test.java;

import main.java.graph.GraphUtils;
import main.java.graph.SimpleGraph;
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

    public static HashSet<SimpleGraph> getTestGraphs() {
        HashSet<SimpleGraph> testGraphs = new HashSet<>();
        for (String filename : testFiles) {
            int[][] adjacencyMatrix = GraphReader.readAdjacencyMatrixFromFile(filename);
            SimpleGraph graph = GraphUtils.readSimpleGraphFromAdjacencyMatrix(adjacencyMatrix);
            testGraphs.add(graph);
        }

        return testGraphs;
    }
}
