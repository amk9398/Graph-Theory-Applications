package main.java.graph.simple;

import main.java.connection.Connection;
import main.java.graph.Graph;
import main.java.graph.GraphType;
import main.java.utils.structures.Edge;
import main.java.utils.structures.EdgeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SimpleGraph implements Graph {
    private EdgeList edgeList = new EdgeList();
    private HashMap<Integer, HashSet<Integer>> neighborMap = new HashMap<>();

    public SimpleGraph(int order) {
        for (int v = 0; v < order; v++) {
            neighborMap.put(v, new HashSet<>());
        }
    }

    SimpleGraph(SimpleGraph simpleGraph) {
        EdgeList edgeList = new EdgeList(simpleGraph.size());

        for (Edge edge : simpleGraph.getEdges()) {
            edgeList.put(edge.clone(), simpleGraph.getEdgeWeight(edge));
        }
        this.edgeList = edgeList;

        for (int v = 0; v < simpleGraph.order(); v++) {
            neighborMap.put(v, new HashSet<>(simpleGraph.neighborsOf(v)));
        }
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (!isValidEdge(v1, v2) || weight == getEdgeWeight(v1, v2)) {
            return;
        }

        edgeList.put(v1, v2, weight);
        neighborMap.get(v1).add(v2);
    }

    @Override
    public int addVertex() {
        neighborMap.put(order(), new HashSet<>());
        return order() - 1;
    }

    @Override
    public void addVertex(int v) {
        addVertex();
        HashSet<Edge> edges = new HashSet<>(size());

        for (Edge edge : new HashSet<>(edgeList.keySet())) {
            removeEdge(edge);
            if (edge.v1 >= v) {
                edge.v1++;
            }
            if (edge.v2 >= v) {
                edge.v2++;
            }
            edges.add(edge);
        }

        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public SimpleGraph clone() {
        return new SimpleGraph(this);
    }

    @Override
    public Graph complement() {
        Graph graph = newInstance(order());

        for (int i = 0; i < order(); i++) {
            for (int j = i + 1; j < order(); j++) {
                if (getEdgeWeight(i, j) == 0) {
                    graph.addEdge(i, j);
                }
                if (getEdgeWeight(j, i) == 0) {
                    graph.addEdge(j, i);
                }
            }
        }

        return graph;
    }

    @Override
    public void contract(int v1, int v2) {
        for (Edge edge : new ArrayList<>(edgeList.keySet())) {
            if (edge.v1 == v1) {
                int weight = getEdgeWeight(v2, edge.v2);
                addEdge(v2, edge.v2, edge.weight + weight);
            } else if (edge.v2 == v1) {
                int weight = getEdgeWeight(edge.v1, v2);
                addEdge(edge.v1, v2, edge.weight + weight);
            }
        }

        removeVertex(v1);
    }

    @Override
    public int degreeOf(int v) {
        if (!isValidVertex(v)) {
            return -1;
        }

        return neighborsOf(v).size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof SimpleGraph that) {
            if (!that.isSimple()
                    || order() != that.order()
                    || size() != that.size()
                    || isDirected() != that.isDirected()) {
                return false;
            }

            return edgeList.equals(that.edgeList);
        } else {
            return false;
        }
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        int[][] adjacencyMatrix = new int[order()][order()];

        for (Edge edge : edgeList.keySet()) {
            adjacencyMatrix[edge.v1][edge.v2] = edgeList.get(edge);
        }

        return adjacencyMatrix;
    }

    @Override
    public Graph getEdgeInducedSubgraph(Set<Edge> edges) {
        HashSet<Integer> vertices = new HashSet<>();

        for (Edge edge : edges) {
            if (hasEdge(edge)) {
                vertices.add(edge.v1);
                vertices.add(edge.v2);
            }
        }

        HashMap<Integer, Integer> vertexMap = new HashMap<>();
        int count = 0;
        for (int v = 0; v < order(); v++) {
            if (vertices.contains(v)) {
                vertexMap.put(v, count++);
            }
        }

        Graph graph = newInstance(vertexMap.size());
        for (Edge edge : edges) {
            if (hasEdge(edge)) {
                graph.addEdge(vertexMap.get(edge.v1), vertexMap.get(edge.v2), edge.weight);
            }
        }

        return graph;
    }

    @Override
    public Set<Edge> getEdges() {
        return edgeList.keySet();
    }

    @Override
    public int getEdgeWeight(int v1, int v2) {
        if (!isValidEdge(v1, v2)) {
            return 0;
        }

        return edgeList.get(v1, v2);
    }

    @Override
    public GraphType getType() {
        return GraphType.SIMPLE;
    }

    @Override
    public Graph getUnderlyingSimpleSubgraph() {
        return this;
    }

    @Override
    public Graph getVertexInducedSubgraph(Set<Integer> vertices) {
        HashMap<Integer, Integer> vertexMap = new HashMap<>();
        int vertexCount = 0;

        for (Integer v : vertices) {
            if (isValidVertex(v)) {
                vertexMap.put(v, vertexCount++);
            }
        }

        Graph vertexInducedSubgraph = newInstance(vertexCount);
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
    public void init(HashMap<Integer, HashSet<Integer>> adjacencyList) {
        for (int v = 0; v < order(); v++) {
            for (int neighbor : adjacencyList.get(v)) {
                addEdge(v, neighbor);
            }
        }
    }

    @Override
    public void init(int[][] adjacencyMatrix) {
        for (int row = 0; row < order(); row++) {
            for (int col = 0; col < order(); col++) {
                addEdge(row, col, adjacencyMatrix[row][col]);
            }
        }
    }

    @Override
    public void init(Edge[] edgeList) {
        for (Edge edge : edgeList) {
            addEdge(edge);
        }
    }

    @Override
    public Graph intersect(Graph graph) {
        int newOrder = Math.min(order(), graph.order());
        Graph newGraph = newInstance(newOrder);

        for (Edge edge : edgeList.keySet()) {
            if (graph.hasEdge(edge)) {
                newGraph.addEdge(edge);
            }
        }

        return newGraph;
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
    public boolean isEdgeDisjoint(Graph graph) {
        for (Edge edge : graph.getEdges()) {
            if (edgeList.containsKey(edge)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEmpty() {
        return edgeList.isEmpty();
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
    public boolean isSimple() {
        return true;
    }

    @Override
    public boolean isSubgraphOf(Graph graph) {
        return isStrictSubgraphOf(graph);
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

        return Connection.kosaraju(this).size();
    }

    @Override
    public int order() {
        return neighborMap.size();
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!isValidEdge(v1, v2) || !hasEdge(v1, v2)) {
            return;
        }

        edgeList.remove(v1, v2);
        neighborMap.get(v1).remove(v2);
    }

    @Override
    public void removeVertex(int v) {
        if (order() == 0) {
            return;
        }

        SimpleGraph graph = (SimpleGraph) newInstance(order() - 1);
        for (Edge edge : getEdges()) {
            if (edge.v1 != v && edge.v2 != v) {
                int i0 = edge.v1 < v ? edge.v1 : edge.v1 - 1;
                int j0 = edge.v2 < v ? edge.v2 : edge.v2 - 1;
                graph.addEdge(i0, j0, getEdgeWeight(edge));
            }
        }

        edgeList = graph.edgeList;
        neighborMap = graph.neighborMap;
    }

    @Override
    public int size() {
        return edgeList.size();
    }

    @Override
    public void swap(int v1, int v2) {
        EdgeList edgeList = new EdgeList();

        for (Edge edge : new HashSet<>(this.edgeList.keySet())) {
            removeEdge(edge);

            if (edge.v1 == v1) {
                edge.v1 = v2;
            } else if (edge.v1 == v2) {
                edge.v1 = v1;
            }
            if (edge.v2 == v1) {
                edge.v2 = v2;
            } else if (edge.v2 == v2) {
                edge.v2 = v1;
            }

            edgeList.put(edge, edge.weight);
        }

        for (Edge edge : new HashSet<>(edgeList.keySet())) {
            addEdge(edge);
        }
    }

    @Override
    public Graph transpose() {
        Graph graph = newInstance(order());

        for (Edge edge : edgeList.keySet()) {
            graph.addEdge(edge.getReverse());
        }

        return graph;
    }

    @Override
    public Graph union(Graph graph) {
        HashSet<Edge> unionEdges = new HashSet<>();
        HashSet<Integer> unionVertices = new HashSet<>();

        for (Edge edge : edgeList.keySet()) {
            unionEdges.add(edge);
            unionVertices.add(edge.v1);
            unionVertices.add(edge.v2);
        }
        for (Edge edge : graph.getEdges()) {
            unionEdges.add(edge);
            unionVertices.add(edge.v1);
            unionVertices.add(edge.v2);
        }

        Graph newGraph = newInstance(unionVertices.size());

        for (Edge edge : unionEdges) {
            newGraph.addEdge(edge);
        }

        return newGraph;
    }

    @Override
    public Graph newInstance(int order) {
        return new SimpleGraph(order);
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

    /**
     * Converts this graph to a specified type.
     *
     * @param graphType The type of graph to convert to. Can be "SIMPLE_GRAPH" or "UNDIRECTED_GRAPH".
     * @return A new graph of the specified type. If the type is invalid, returns null.
     */
    public SimpleGraph as(GraphType graphType) {
        switch (graphType) {
            case SIMPLE: return clone();
            case UNDIRECTED:
                UndirectedGraph graph = new UndirectedGraph(order());
                for (Edge edge : edgeList.keySet()) {
                    graph.addEdge(edge);
                }
                return graph;
            default: return null;
        }
    }

    /**
     * Checks if this graph is a strict subgraph of another graph by checking if all edges
     * in this graph are in the supergraph.
     *
     * @param graph The graph to compare against.
     * @return True if this graph is a strict subgraph of the specified graph, false otherwise.
     */
    public boolean isStrictSubgraphOf(Graph graph) {
        for (Edge edge : edgeList.keySet()) {
            if (!graph.hasEdge(edge)) {
                return false;
            }
        }

        return true;
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