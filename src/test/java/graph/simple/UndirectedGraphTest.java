package test.java.graph.simple;

import main.java.graph.Graph;
import main.java.utils.structures.Edge;
import main.java.graph.simple.SimpleGraph;
import main.java.graph.simple.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

public class UndirectedGraphTest extends UnitTestClass {
    @Test
    public void testAddEdge() {
        UndirectedGraph graph = getUndirectedGraph(EMPTY).clone();
        testAddEdge(graph, new Edge(0, 2, 0), false);
        testAddEdge(graph, new Edge(0, 3), false);
        testAddEdge(graph, new Edge(-1, 1), false);
        testAddEdge(graph, new Edge(0, 1), true);
        testAddEdge(graph, new Edge(0, 1), false);
    }

    @Test
    public void testCotree() {
        test(name -> {
            if (getProfile(name).mstWeight == -1) {
                return;
            }

            Graph cotree = getUndirectedGraph(name).cotree();
            Assert.assertNotNull(cotree);
            Assert.assertTrue(getUndirectedGraph(name).isCotreeOf(cotree));
        }, "testCotree");
    }

    @Test
    public void testIsComplete() {
        test(name -> Assert.assertEquals(getProfile(name).complete, getUndirectedGraph(name).isComplete()),
                "testIsComplete");
    }

    @Test
    public void testIsConnected() {
        test(name -> {
            int numWeakComponents = getProfile(name).numWeakComponents;
            boolean isConnected = numWeakComponents == 0 || numWeakComponents == 1;
            Assert.assertEquals(isConnected, getUndirectedGraph(name).isConnected());
        }, "testIsConnected");
    }

    @Test
    public void testIsCyclic() {
        test(name -> Assert.assertEquals(getProfile(name).undirectedCyclic, getUndirectedGraph(name).isCyclic()),
                "testIsCyclic");
    }

    @Test
    public void testNumComponents() {
        test(name -> Assert.assertEquals(getProfile(name).numWeakComponents, getUndirectedGraph(name).numComponents()),
                "testNumComponents");
    }

    @Test
    public void testRemoveEdge() {
        UndirectedGraph graph = getUndirectedGraph(SINGLE_EDGE).clone();
        testRemoveEdge(graph, new Edge(0, 2), false);
        testRemoveEdge(graph, new Edge(0, 3), false);
        testRemoveEdge(graph, new Edge(-1, 1), false);
        testRemoveEdge(graph, new Edge(0, 1), true);
        testRemoveEdge(graph, new Edge(0, 1), false);
    }

    private void testAddEdge(UndirectedGraph graph, Edge edge, boolean assertChanged) {
        int weightBefore = graph.getEdgeWeight(edge);
        int weightBeforeR = graph.getEdgeWeight(edge.getReverse());

        graph.addEdge(edge);
        Assert.assertEquals(assertChanged ? edge.weight : weightBefore, graph.getEdgeWeight(edge));
        Assert.assertEquals(assertChanged ? edge.weight : weightBeforeR, graph.getEdgeWeight(edge.getReverse()));
    }

    private void testRemoveEdge(SimpleGraph graph, Edge edge, boolean assertChanged) {
        int weightBefore = graph.getEdgeWeight(edge);
        int weightBeforeR = graph.getEdgeWeight(edge.getReverse());

        graph.removeEdge(edge);
        Assert.assertEquals(assertChanged ? 0 : weightBefore, graph.getEdgeWeight(edge));
        Assert.assertEquals(assertChanged ? 0 : weightBeforeR, graph.getEdgeWeight(edge.getReverse()));
    }
}