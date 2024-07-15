package test.java.connection;

import main.java.connection.Connection;
import main.java.graph.AbstractGraph;
import main.java.graph.Edge;
import main.java.graph.SimpleGraph;
import main.java.utils.io.GraphReader;
import org.junit.Assert;
import org.junit.Test;
import test.java.TestUtils;

import java.util.ArrayList;

public class ConnectionTest {
    @Test
    public void testAllGraphs() {
        for (SimpleGraph graph : TestUtils.getTestGraphs()) {
            Connection.kruskal(graph);
        }
    }

    @Test
    public void testConnectedGraph() {
        SimpleGraph connectedGraph = GraphReader.readSimpleGraphFromFile("src/data/connectedGraph.txt");
        Assert.assertNotNull(connectedGraph);

        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(new Edge(0, 1, 2));
        expected.add(new Edge(0, 2, 3));
        expected.add(new Edge(0, 3, 4));
        expected.add(new Edge(0, 4, 5));

        ArrayList<Edge> actual = Connection.kruskal(connectedGraph).getEdgeList();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testLargeGraph() {
        SimpleGraph largeGraph = GraphReader.readSimpleGraphFromFile("src/data/largeGraph.txt");
        Assert.assertNotNull(largeGraph);

        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(new Edge(2, 5, 2));
        expected.add(new Edge(3, 4, 6));
        expected.add(new Edge(0, 1, 7));
        expected.add(new Edge(0, 2, 9));
        expected.add(new Edge(4, 5, 9));

        ArrayList<Edge> actual = Connection.kruskal(largeGraph).getEdgeList();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testMediumGraph() {
        SimpleGraph mediumGraph = GraphReader.readSimpleGraphFromFile("src/data/mediumGraph.txt");
        Assert.assertNotNull(mediumGraph);

        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(new Edge(1, 2, 1));
        expected.add(new Edge(3, 4, 1));
        expected.add(new Edge(2, 3, 2));
        expected.add(new Edge(0, 1, 3));

        ArrayList<Edge> actual = Connection.kruskal(mediumGraph).getEdgeList();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testSimpleGraph() {
        SimpleGraph simpleGraph = GraphReader.readSimpleGraphFromFile("src/data/simpleGraph.txt");
        Assert.assertNotNull(simpleGraph);

        ArrayList<Edge> expected = new ArrayList<>();
        expected.add(new Edge(0, 1, 1));
        expected.add(new Edge(2, 3, 1));
        expected.add(new Edge(1, 3, 2));

        ArrayList<Edge> actual = Connection.kruskal(simpleGraph).getEdgeList();

        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void testSparseGraph() {
        SimpleGraph sparseGraph = GraphReader.readSimpleGraphFromFile("src/data/sparseGraph.txt");
        Assert.assertNotNull(sparseGraph);

        int actual = 0;
        for (Edge edge : Connection.kruskal(sparseGraph).getEdgeList()) {
            actual += edge.weight;
        }

        Assert.assertEquals(19, actual);
    }
}
