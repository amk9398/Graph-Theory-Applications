package test.java.graph.simple;

import main.java.graph.AbstractGraph;
import main.java.graph.Edge;
import main.java.graph.simple.SimpleGraph;
import main.java.utils.io.GraphReader;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

import java.util.HashSet;

public class SimpleGraphTest extends UnitTestClass {
    @Test
    public void testAddEdge() {
        SimpleGraph graph = getSimpleGraph(EMPTY).clone();
        testAddEdge(graph, new Edge(0, 2, 0), false);
        testAddEdge(graph, new Edge(0, 3), false);
        testAddEdge(graph, new Edge(-1, 1), false);
        testAddEdge(graph, new Edge(0, 1), true);
        testAddEdge(graph, new Edge(0, 1), false);
    }

    @Test
    public void testAddVertex() {
        for (String name : getTestGraphs()) {
            SimpleGraph graph = getSimpleGraph(name);
            SimpleGraph clone = graph.clone();
            int v = clone.addVertex();
            Assert.assertEquals(graph.order() + 1, clone.order());
            Assert.assertEquals(0, clone.degreeOf(v));
            Assert.assertTrue(clone.neighborsOf(v).isEmpty());
        }
    }

    @Test
    public void testClone() {
        for (String name : getTestGraphs()) {
            SimpleGraph graph = getSimpleGraph(name);
            SimpleGraph clone = graph.clone();

            Assert.assertEquals(graph.size(), clone.size());
            for (int v = 0; v < graph.order(); v++) {
                Assert.assertArrayEquals(graph.getAdjacencyMatrix()[v], clone.getAdjacencyMatrix()[v]);
                Assert.assertEquals(graph.degreeOf(v), clone.degreeOf(v));
                Assert.assertEquals(graph.neighborsOf(v), clone.neighborsOf(v));
            }
            Assert.assertEquals(graph.getEdges(), clone.getEdges());
        }
    }

    @Test
    public void testComplement() {
        int[][] complementAdjMatrix = new int[][]{
                {0, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 0, 0, 0}
        };
        SimpleGraph complement = GraphReader.readSimpleGraphFromAdjacencyMatrix(complementAdjMatrix);
        Assert.assertTrue(getSimpleGraph(SIMPLE).complement().isIdentical(complement));
    }

    @Test
    public void testDegreeOf() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (int i = 0; i < graph.order(); i++) {
                Assert.assertEquals(getProfile(name).degrees[i], graph.degreeOf(i));
            }
        }, "SimpleGraphTest.testDegreeOf");
    }

    @Test
    public void testGetEdgeInducedSubgraph() {
        HashSet<Edge> edges = new HashSet<>();
        testGetEdgeInducedSubgraph(getSimpleGraph(ZERO), edges, 0);

        edges.add(new Edge(0, 1));
        AbstractGraph induced = getSimpleGraph(EMPTY).getEdgeInducedSubgraph(edges);
        Assert.assertEquals(0, induced.order());
        Assert.assertEquals(new HashSet<>(), induced.getEdges());

        edges.add(new Edge(1, 2));
        testGetEdgeInducedSubgraph(getSimpleGraph(PATH), edges, 3);

        edges.clear();
        SimpleGraph simpleGraph = getSimpleGraph(SIMPLE);
        edges.addAll(simpleGraph.getEdges());
        testGetEdgeInducedSubgraph(simpleGraph, edges, simpleGraph.order());
    }

    @Test
    public void testGetVertexInducedSubgraph() {
        HashSet<Integer> vertices = new HashSet<>();
        testGetVertexInducedSubgraph(getSimpleGraph(ZERO), vertices);

        AbstractGraph induced = getSimpleGraph(ZERO).getVertexInducedSubgraph(vertices);
        Assert.assertEquals(0, induced.order());

        vertices.add(0);
        testGetVertexInducedSubgraph(getSimpleGraph(EMPTY), vertices);

        vertices.add(1);
        testGetVertexInducedSubgraph(getSimpleGraph(PATH), vertices);
        vertices.clear();
        for (int v = 0; v < getSimpleGraph(SIMPLE).order(); v++) {
            vertices.add(v);
        }
        testGetVertexInducedSubgraph(getSimpleGraph(SIMPLE), vertices);
    }

    @Test
    public void testIsComplete() {
        test(name -> Assert.assertEquals(getProfile(name).complete, getSimpleGraph(name).isComplete()),
                "SimpleGraphTest.testIsComplete");
    }

    @Test
    public void testIsCutEdge() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (Edge edge : graph.getEdges()) {
                Assert.assertEquals(getProfile(name).directedCutEdges.contains(edge), graph.isCutEdge(edge));
            }
        }, "SimpleGraphTest.testIsCutEdge");
    }

    @Test
    public void testIsCutVertex() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (int i = 0; i < graph.order(); i++) {
                Assert.assertEquals(getProfile(name).directedCutVertices.contains(i), graph.isCutVertex(i));
            }
        }, "SimpleGraphTest.testIsCutVertex");
    }

    @Test
    public void testIsCyclic() {
        test(name -> Assert.assertEquals(getProfile(name).directedCyclic, getSimpleGraph(name).isCyclic()),
                "SimpleGraphTest.testIsCyclic");
    }

    @Test
    public void testIsEmpty() {
        test(name -> Assert.assertEquals(getProfile(name).empty, getSimpleGraph(name).isEmpty()),
                "SimpleGraphTest.testIsEmpty");
    }

    @Test
    public void testIsEulerian() {
        test(name -> Assert.assertEquals(getProfile(name).eulerian, getSimpleGraph(name).isEulerian()),
                "SimpleGraphTest.testIsEulerian");
    }

    @Test
    public void testIsIdentical() {
        for (String name : getTestGraphs()) {
            SimpleGraph graph = getSimpleGraph(name);
            Assert.assertTrue(graph.isIdentical(graph.clone()));
        }
    }

    @Test
    public void testMaxDegree() {
        test(name -> Assert.assertEquals(getProfile(name).maxDegree, getSimpleGraph(name).maxDegree()),
                "SimpleGraphTest.testMaxDegree");
    }

    @Test
    public void testMinDegree() {
        test(name -> Assert.assertEquals(getProfile(name).minDegree, getSimpleGraph(name).minDegree()),
                "SimpleGraphTest.testMinDegree");
    }

    @Test
    public void testOrder() {
        test(name -> Assert.assertEquals(getProfile(name).order, getSimpleGraph(name).order()),
                "SimpleGraphTest.testOrder");
    }

    @Test
    public void testRemoveEdge() {
        SimpleGraph graph = getSimpleGraph(SINGLE_EDGE).clone();
        testRemoveEdge(graph, new Edge(0, 2), false);
        testRemoveEdge(graph, new Edge(0, 3), false);
        testRemoveEdge(graph, new Edge(-1, 1), false);
        testRemoveEdge(graph, new Edge(0, 1), true);
        testRemoveEdge(graph, new Edge(0, 1), false);
    }

    @Test
    public void testRemoveVertex() {
        for (String name : getTestGraphs()) {
            SimpleGraph graph = getSimpleGraph(name);
            SimpleGraph clone = graph.clone();
            HashSet<Integer> incoming = new HashSet<>();

            for (Edge edge : graph.getEdges()) {
                if (edge.v2 == 0) {
                    incoming.add(edge.v1);
                }
            }
            clone.removeVertex(0);

            Assert.assertEquals(Math.max(graph.order() - 1, 0), clone.order());
            for (Integer v : incoming) {
                Assert.assertEquals(graph.degreeOf(v) - 1, clone.degreeOf(v - 1));
            }
        }
    }

    @Test
    public void testReverse() {
        SimpleGraph directedTree = getSimpleGraph(DIRECTED_TREE);
        SimpleGraph dtClone = directedTree.clone();
        dtClone.reverse();

        for (int i = 0; i < directedTree.order(); i++) {
            for (int j = 0; j < directedTree.order(); j++) {
                Assert.assertEquals(directedTree.getAdjacencyMatrix()[i][j], dtClone.getAdjacencyMatrix()[j][i]);
            }
        }
    }

    @Test
    public void testSize() {
        test(name -> Assert.assertEquals(getProfile(name).size, getSimpleGraph(name).size()),
                "SimpleGraphTest.testSize");
    }

    private void testAddEdge(SimpleGraph graph, Edge edge, boolean assertChanged) {
        int weightBefore = graph.getEdgeWeight(edge);

        graph.addEdge(edge);
        Assert.assertEquals(assertChanged ? edge.weight : weightBefore, graph.getEdgeWeight(edge));
    }

    private void testGetEdgeInducedSubgraph(SimpleGraph graph, HashSet<Edge> edges, int expectedOrder) {
        AbstractGraph induced = graph.getEdgeInducedSubgraph(edges);
        Assert.assertEquals(expectedOrder, induced.order());
        Assert.assertEquals(edges, induced.getEdges());
    }

    private void testGetVertexInducedSubgraph(SimpleGraph graph, HashSet<Integer> vertices) {
        AbstractGraph induced = graph.getVertexInducedSubgraph(vertices);
        Assert.assertEquals(vertices.size(), induced.order());
    }

    private void testRemoveEdge(SimpleGraph graph, Edge edge, boolean assertChanged) {
        int weightBefore = graph.getEdgeWeight(edge);

        graph.removeEdge(edge);
        Assert.assertEquals(assertChanged ? 0 : weightBefore, graph.getEdgeWeight(edge));
    }
}