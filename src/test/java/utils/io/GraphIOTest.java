package test.java.utils.io;

import main.java.graph.Graph;
import main.java.graph.GraphBuilder;
import main.java.graph.GraphType;
import main.java.utils.Log;
import main.java.utils.io.FileType;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class GraphIOTest {
    private static final String TEST_LOCATION = "src/data/graphs/test_graph.txt";

    @Test
    public void testReadAndWrite() {
        testReadAndWriteFileType(FileType.ADJACENCY_LIST);
        testReadAndWriteFileType(FileType.ADJACENCY_MATRIX);
        testReadAndWriteFileType(FileType.EDGE_LIST);
    }

    private ArrayList<Graph> getTestGraphs(boolean weighted) {
        ArrayList<Graph> testGraphs = new ArrayList<>();

        GraphType[] graphTypes = new GraphType[] {
                GraphType.SIMPLE,
                GraphType.UNDIRECTED
        };

        for (GraphType graphType : graphTypes) {
            testGraphs.add(graphType.newInstance());

            for (int i = 0; i < 3; i++) {
                int order = (int) Math.pow(10, i);
                Graph graph = new GraphBuilder()
                        .graphType(graphType)
                        .order(order)
                        .size(GraphBuilder.Size.MEDIUM)
                        .weighted(weighted)
                        .build();
                testGraphs.add(graph);
            }
        }

        return testGraphs;
    }

    private void testReadAndWriteFileType(FileType fileType) {
        for (Graph graph : getTestGraphs(fileType != FileType.ADJACENCY_LIST)) {
            testReadAndWriteFileType(graph, fileType);
        }
    }

    private void testReadAndWriteFileType(Graph graph, FileType fileType) {
        graph.write(fileType, TEST_LOCATION);
        Optional<Graph> newGraph = Graph.read(TEST_LOCATION);

        File testFile = new File(TEST_LOCATION);
        if (testFile.delete()) {
            Log.d("Successfully deleted the test file");
        } else {
            Log.w("Failed to delete the test file");
        }

        Assert.assertFalse(newGraph.isEmpty());
        Assert.assertEquals(graph, newGraph.get());
    }
}