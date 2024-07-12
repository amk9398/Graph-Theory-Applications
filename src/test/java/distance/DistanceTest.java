package test.java.distance;

import main.java.distance.Distance;
import main.java.graph.Graph;
import main.java.utils.io.GraphReader;
import org.junit.Assert;
import org.junit.Test;
import test.java.TestUtils;

public class DistanceTest {
    @Test
    public void testAllGraphs() {
        for (Graph graph : TestUtils.getTestGraphs()) {
            Distance.dijkstra(graph, 0);
        }
    }

    @Test
    public void testConnectedGraph() {
        Graph connectedGraph = GraphReader.readGraphFromFile("src/data/connectedGraph.txt");
        Assert.assertNotNull(connectedGraph);

        int[] distances = Distance.dijkstra(connectedGraph, 0);
        Assert.assertArrayEquals(new int[]{0, 2, 3, 4, 5}, distances);

        distances = Distance.dijkstra(connectedGraph, 1);
        Assert.assertArrayEquals(new int[]{2, 0, 5, 6, 7}, distances);

        distances = Distance.dijkstra(connectedGraph, 2);
        Assert.assertArrayEquals(new int[]{3, 5, 0, 7, 8}, distances);

        distances = Distance.dijkstra(connectedGraph, 3);
        Assert.assertArrayEquals(new int[]{4, 6, 7, 0, 9}, distances);

        distances = Distance.dijkstra(connectedGraph, 4);
        Assert.assertArrayEquals(new int[]{5, 7, 8, 9, 0}, distances);
    }

    @Test
    public void testLargeGraph() {
        Graph largeGraph = GraphReader.readGraphFromFile("src/data/largeGraph.txt");
        Assert.assertNotNull(largeGraph);

        int[] distances = Distance.dijkstra(largeGraph, 0);
        Assert.assertArrayEquals(new int[]{0, 7, 9, 20, 20, 11}, distances);

        distances = Distance.dijkstra(largeGraph, 1);
        Assert.assertArrayEquals(new int[]{7, 0, 10, 15, 21, 12}, distances);

        distances = Distance.dijkstra(largeGraph, 2);
        Assert.assertArrayEquals(new int[]{9, 10, 0, 11, 11, 2}, distances);

        distances = Distance.dijkstra(largeGraph, 3);
        Assert.assertArrayEquals(new int[]{20, 15, 11, 0, 6, 13}, distances);

        distances = Distance.dijkstra(largeGraph, 4);
        Assert.assertArrayEquals(new int[]{20, 21, 11, 6, 0, 9}, distances);

        distances = Distance.dijkstra(largeGraph, 5);
        Assert.assertArrayEquals(new int[]{11, 12, 2, 13, 9, 0}, distances);
    }

    @Test
    public void testMediumGraph() {
        Graph mediumGraph = GraphReader.readGraphFromFile("src/data/mediumGraph.txt");
        Assert.assertNotNull(mediumGraph);

        int[] distances = Distance.dijkstra(mediumGraph, 0);
        Assert.assertArrayEquals(new int[]{0, 3, 4, 6, 7}, distances);

        distances = Distance.dijkstra(mediumGraph, 1);
        Assert.assertArrayEquals(new int[]{3, 0, 1, 3, 4}, distances);

        distances = Distance.dijkstra(mediumGraph, 2);
        Assert.assertArrayEquals(new int[]{4, 1, 0, 2, 3}, distances);

        distances = Distance.dijkstra(mediumGraph, 3);
        Assert.assertArrayEquals(new int[]{6, 3, 2, 0, 1}, distances);

        distances = Distance.dijkstra(mediumGraph, 4);
        Assert.assertArrayEquals(new int[]{7, 4, 3, 1, 0}, distances);
    }

    @Test
    public void testSimpleGraph() {
        Graph simpleGraph = GraphReader.readGraphFromFile("src/data/simpleGraph.txt");
        Assert.assertNotNull(simpleGraph);

        int[] distances = Distance.dijkstra(simpleGraph, 0);
        Assert.assertArrayEquals(new int[]{0, 1, 4, 3}, distances);

        distances = Distance.dijkstra(simpleGraph, 1);
        Assert.assertArrayEquals(new int[]{1, 0, 3, 2}, distances);

        distances = Distance.dijkstra(simpleGraph, 2);
        Assert.assertArrayEquals(new int[]{4, 3, 0, 1}, distances);

        distances = Distance.dijkstra(simpleGraph, 3);
        Assert.assertArrayEquals(new int[]{3, 2, 1, 0}, distances);
    }

    @Test
    public void testSparseGraph() {
        Graph sparseGraph = GraphReader.readGraphFromFile("src/data/sparseGraph.txt");
        Assert.assertNotNull(sparseGraph);

        int[] distances = Distance.dijkstra(sparseGraph, 0);
        Assert.assertArrayEquals(new int[]{0, 3, 7, 1, 2, 13, 6}, distances);

        distances = Distance.dijkstra(sparseGraph, 1);
        Assert.assertArrayEquals(new int[]{3, 0, 5, 2, 2, 11, 6}, distances);

        distances = Distance.dijkstra(sparseGraph, 2);
        Assert.assertArrayEquals(new int[]{7, 5, 0, 6, 5, 6, 9}, distances);

        distances = Distance.dijkstra(sparseGraph, 3);
        Assert.assertArrayEquals(new int[]{1, 2, 6, 0, 1, 12, 5}, distances);

        distances = Distance.dijkstra(sparseGraph, 4);
        Assert.assertArrayEquals(new int[]{2, 2, 5, 1, 0, 11, 4}, distances);

        distances = Distance.dijkstra(sparseGraph, 5);
        Assert.assertArrayEquals(new int[]{13, 11, 6, 12, 11, 0, 7}, distances);

        distances = Distance.dijkstra(sparseGraph, 6);
        Assert.assertArrayEquals(new int[]{6, 6, 9, 5, 4, 7, 0}, distances);
    }
}
