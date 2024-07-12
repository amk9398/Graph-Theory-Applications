package test.java.walk;

import main.java.graph.Graph;
import main.java.utils.io.GraphReader;
import main.java.walk.Tour;
import org.junit.Assert;
import org.junit.Test;
import test.java.TestUtils;

import java.util.ArrayList;

public class TourTest {
    @Test
    public void testAllGraphs() {
        for (Graph graph : TestUtils.getTestGraphs()) {
            Tour.fleury(graph);
        }
    }

    @Test
    public void testConnectedGraph() {
        Graph connectedGraph = GraphReader.readGraphFromFile("src/data/connectedGraph.txt");
        Assert.assertNotNull(connectedGraph);

        ArrayList<Integer> tour = Tour.fleury(connectedGraph);
        Assert.assertEquals(11, tour.size());
        Assert.assertTrue(Tour.isEulerTour(connectedGraph, tour));
    }

    @Test
    public void testLargeGraph() {
        Graph largeGraph = GraphReader.readGraphFromFile("src/data/largeGraph.txt");
        Assert.assertNotNull(largeGraph);

        ArrayList<Integer> tour = Tour.fleury(largeGraph);
        Assert.assertFalse(Tour.isEulerTour(largeGraph, tour));
    }

    @Test
    public void testMediumGraph() {
        Graph mediumGraph = GraphReader.readGraphFromFile("src/data/mediumGraph.txt");
        Assert.assertNotNull(mediumGraph);

        ArrayList<Integer> tour = Tour.fleury(mediumGraph);
        Assert.assertEquals(6, tour.size());
        Assert.assertFalse(Tour.isEulerTour(mediumGraph, tour));
    }

    @Test
    public void testSimpleGraph() {
        Graph simpleGraph = GraphReader.readGraphFromFile("src/data/simpleGraph.txt");
        Assert.assertNotNull(simpleGraph);

        ArrayList<Integer> tour = Tour.fleury(simpleGraph);
        Assert.assertEquals(6, tour.size());
        Assert.assertFalse(Tour.isEulerTour(simpleGraph, tour));
    }

    @Test
    public void testSparseGraph() {
        Graph sparseGraph = GraphReader.readGraphFromFile("src/data/sparseGraph.txt");
        Assert.assertNotNull(sparseGraph);

        ArrayList<Integer> tour = Tour.fleury(sparseGraph);
        Assert.assertFalse(Tour.isEulerTour(sparseGraph, tour));
    }
}
