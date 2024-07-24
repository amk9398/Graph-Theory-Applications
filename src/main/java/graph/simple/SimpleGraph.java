package main.java.graph.simple;

import main.java.connection.Connection;
import main.java.graph.AbstractGraph;
import main.java.graph.Edge;
import main.java.utils.Utils;

import java.util.*;

public class SimpleGraph extends AbstractGraph {
    public static final String SIMPLE_GRAPH = "SIMPLE";
    public static final String UNDIRECTED_GRAPH = "UNDIRECTED_GRAPH";

    protected int[][] adjacencyMatrix;
    protected HashMap<Integer, Integer> degreeMap = new HashMap<>();
    protected HashSet<Edge> edges = new HashSet<>();
    protected HashMap<Integer, HashSet<Integer>> neighborMap = new HashMap<>();

    public SimpleGraph(int order) {
        adjacencyMatrix = new int[order][order];

        for (int v = 0; v < order; v++) {
            degreeMap.put(v, 0);
            neighborMap.put(v, new HashSet<>());
        }
    }

    SimpleGraph(SimpleGraph simpleGraph) {
        adjacencyMatrix = Utils.deepCopy2dIntArray(simpleGraph.getAdjacencyMatrix());

        for (int v = 0; v < simpleGraph.order(); v++) {
            degreeMap.put(v, simpleGraph.degreeOf(v));
            neighborMap.put(v, new HashSet<>(simpleGraph.neighborsOf(v)));
        }

        edges = new HashSet<>(simpleGraph.getEdges());
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (!isValidEdge(v1, v2) || weight == getEdgeWeight(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = weight;
        degreeMap.put(v1, degreeOf(v1) + 1);
        edges.add(new Edge(v1, v2, weight));
        neighborMap.get(v1).add(v2);
    }

    @Override
    public int addVertex() {
        int v = order();
        int[][] matrix = new int[v + 1][v + 1];

        for (Edge edge : edges) {
            matrix[edge.v1][edge.v2] = edge.weight;
        }
        adjacencyMatrix = matrix;
        degreeMap.put(v, 0);
        neighborMap.put(v, new HashSet<>());

        return v;
    }

    @Override
    public SimpleGraph clone() {
        return new SimpleGraph(this);
    }

    @Override
    public AbstractGraph complement() {
        SimpleGraph simpleGraph = new SimpleGraph(order());

        for (int i = 0; i < order(); i++) {
            for (int j = 0; j < order(); j++) {
                if (i != j && getEdgeWeight(i, j) == 0) {
                    simpleGraph.addEdge(i, j);
                }
            }
        }

        return simpleGraph;
    }

    @Override
    public int degreeOf(int v) {
        if (!isValidVertex(v)) {
            return -1;
        }

        return degreeMap.get(v);
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public AbstractGraph getEdgeInducedSubgraph(Set<Edge> edges) {
        HashSet<Integer> vertices = new HashSet<>();

        for (Edge edge : edges) {
            if (hasEdge(edge)) {
                vertices.add(edge.v1);
                vertices.add(edge.v2);
            }
        }

        SimpleGraph edgeInducedSubgraph = new SimpleGraph(vertices.size());
        for (Edge edge : edges) {
            if (hasEdge(edge)) {
                edgeInducedSubgraph.addEdge(edge);
            }
        }

        return edgeInducedSubgraph;
    }

    @Override
    public HashSet<Edge> getEdges() {
        return edges;
    }

    @Override
    public int getEdgeWeight(int v1, int v2) {
        if (!isValidEdge(v1, v2)) {
            return 0;
        }

        return adjacencyMatrix[v1][v2];
    }

    @Override
    public AbstractGraph getVertexInducedSubgraph(Set<Integer> vertices) {
        HashMap<Integer, Integer> vertexMap = new HashMap<>();
        int vertexCount = 0;

        for (Integer v : vertices) {
            if (isValidVertex(v)) {
                vertexMap.put(v, vertexCount++);
            }
        }

        SimpleGraph vertexInducedSubgraph = new SimpleGraph(vertexCount);
        for (Integer v : vertices) {
            if (!isValidVertex(v)) {
                continue;
            }

            for (Integer n : neighborsOf(v)) {
                Integer mapped = vertexMap.get(n);
                if (mapped != null) {
                    vertexInducedSubgraph.addEdge(v, mapped, getEdgeWeight(v, n));
                }
            }
        }

        return vertexInducedSubgraph;
    }

    @Override
    public boolean hasEdge(int v1, int v2) {
        if (!isValidEdge(v1, v2)) {
            return false;
        }

        return getEdgeWeight(v1, v2) != 0;
    }

    @Override
    public boolean isComplete() {
        return size() == order() * (order() - 1);
    }

    @Override
    public boolean isConnected() {
        return numComponents() == 1;
    }

    @Override
    public boolean isCutEdge(int v1, int v2) {
        SimpleGraph clone = clone();
        clone.removeEdge(v1, v2);

        return numComponents() < clone.numComponents();
    }

    @Override
    public boolean isCutVertex(int v) {
        SimpleGraph clone = clone();
        clone.removeVertex(v);

        return numComponents() < clone.numComponents();
    }

    @Override
    public boolean isCyclic() {
        if (isEmpty()) {
            return false;
        }

        for (int v = 0; v < order(); v++) {
            if (isCyclicH(v, new HashSet<>(), -1)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public boolean isEulerian() {
        for (int i = 0; i < order(); i++) {
            if (degreeOf(i) % 2 == 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isIdentical(AbstractGraph graph) {
        if (!graph.isSimple()
                || order() != graph.order()
                || size() != graph.size()
                || isDirected() != graph.isDirected()) {
            return false;
        }

        return Arrays.deepEquals(adjacencyMatrix, graph.getAdjacencyMatrix());
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public boolean isTree() {
        if (order() == 0) {
            return true;
        }

        if (isCyclic()) {
            return false;
        }

        for (int v = 0; v < order(); v++) {
            if (dfs(v).size() == order()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int maxDegree() {
        if (order() == 0) {
            return 0;
        }

        int maxDegree = Integer.MIN_VALUE;
        for (int v = 0; v < order(); v++) {
            if (degreeOf(v) > maxDegree) {
                maxDegree = degreeOf(v);
            }
        }

        return maxDegree;
    }

    @Override
    public int minDegree() {
        if (order() == 0) {
            return 0;
        }

        int minDegree = Integer.MAX_VALUE;
        for (int v = 0; v < order(); v++) {
            if (degreeOf(v) < minDegree) {
                minDegree = degreeOf(v);
            }
        }

        return minDegree;
    }

    @Override
    public HashSet<Integer> neighborsOf(int v) {
        return neighborMap.get(v);
    }

    @Override
    public int numComponents() {
        if (order() == 0) {
            return 0;
        }

        return Connection.kosaraju(this);
    }

    @Override
    public int order() {
        return adjacencyMatrix.length;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!isValidEdge(v1, v2) || !hasEdge(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = 0;
        degreeMap.put(v1, degreeOf(v1) - 1);
        edges.remove(new Edge(v1, v2));
        neighborMap.get(v1).remove(v2);
    }

    @Override
    public void removeVertex(int v) {
        if (order() == 0) {
            return;
        }

        SimpleGraph simpleGraph = new SimpleGraph(order() - 1);
        for (Edge edge : getEdges()) {
            if (edge.v1 != v && edge.v2 != v) {
                int i0 = edge.v1 < v ? edge.v1 : edge.v1 - 1;
                int j0 = edge.v2 < v ? edge.v2 : edge.v2 - 1;
                simpleGraph.addEdge(i0, j0, getEdgeWeight(edge));
            }
        }

        adjacencyMatrix = simpleGraph.adjacencyMatrix;
        degreeMap = simpleGraph.degreeMap;
        edges = simpleGraph.edges;
        neighborMap = simpleGraph.neighborMap;
    }

    @Override
    public void reverse() {
        ArrayList<Edge> edgeList = new ArrayList<>(edges);

        for (Edge edge : edgeList) {
            if (!hasEdge(edge.v2, edge.v1)) {
                removeEdge(edge);
                addEdge(edge.v2, edge.v1, edge.weight);
            }
        }
    }

    @Override
    public int size() {
        return edges.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < order(); i++) {
            for (int j = 0; j < order(); j++) {
                sb.append(getEdgeWeight(i, j)).append(j == order() - 1 ? "" : ",");
            }
            sb.append(i == order() - 1 ? "" : "\n");
        }

        return sb.toString();
    }

    public SimpleGraph as(String type) {
        SimpleGraph graph;

        switch (type) {
            case SIMPLE_GRAPH -> graph = clone();
            case UNDIRECTED_GRAPH -> {
                graph = new UndirectedGraph(order());
                for (Edge edge : edges) {
                    graph.addEdge(edge);
                }
            }
            default -> graph = null;
        }

        return graph;
    }

    protected boolean isValidEdge(Edge edge) {
        return isValidEdge(edge.v1, edge.v2);
    }

    protected boolean isValidEdge(int v1, int v2) {
        return isValidVertex(v1) && isValidVertex(v2) && v1 != v2;
    }

    protected boolean isValidVertex(int v) {
        return v >= 0 && v < order();
    }

    private boolean isCyclicH(int v, HashSet<Integer> visited, int parent) {
        visited.add(v);
        for (int i : neighborsOf(v)) {
            if (!visited.contains(i)) {
                if (isCyclicH(i, visited, v)) {
                    return true;
                }
            } else if (i == parent) {
                return true;
            }
        }

        return false;
    }
}