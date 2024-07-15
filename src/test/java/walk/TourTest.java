package test.java.walk;

import main.java.graph.SimpleGraph;
import main.java.utils.io.GraphReader;
import main.java.walk.Tour;
import org.junit.Assert;
import org.junit.Test;
import test.java.TestUtils;

import java.util.ArrayList;

public class TourTest {
    @Test
    public void testAllGraphs() {
        for (SimpleGraph graph : TestUtils.getTestGraphs()) {
            Tour.fleury(graph);
        }
    }

    @Test
    public void testConnectedGraph() {
        SimpleGraph connectedGraph = GraphReader.readSimpleGraphFromFile("src/data/connectedGraph.txt");
        Assert.assertNotNull(connectedGraph);

        ArrayList<Integer> tour = Tour.fleury(connectedGraph);
        Assert.assertEquals(11, tour.size());
    }

    @Test
    public void testLargeGraph() {
        SimpleGraph largeGraph = GraphReader.readSimpleGraphFromFile("src/data/largeGraph.txt");
        Assert.assertNotNull(largeGraph);

        ArrayList<Integer> tour = Tour.fleury(largeGraph);
    }

    @Test
    public void testMediumGraph() {
        SimpleGraph mediumGraph = GraphReader.readSimpleGraphFromFile("src/data/mediumGraph.txt");
        Assert.assertNotNull(mediumGraph);

        ArrayList<Integer> tour = Tour.fleury(mediumGraph);
        Assert.assertEquals(6, tour.size());
    }

    @Test
    public void testSimpleGraph() {
        SimpleGraph simpleGraph = GraphReader.readSimpleGraphFromFile("src/data/simpleGraph.txt");
        Assert.assertNotNull(simpleGraph);

        ArrayList<Integer> tour = Tour.fleury(simpleGraph);
        Assert.assertEquals(6, tour.size());
    }

    @Test
    public void testSparseGraph() {
        SimpleGraph sparseGraph = GraphReader.readSimpleGraphFromFile("src/data/sparseGraph.txt");
        Assert.assertNotNull(sparseGraph);

        ArrayList<Integer> tour = Tour.fleury(sparseGraph);
    }
}
