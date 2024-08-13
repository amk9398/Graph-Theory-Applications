package main.java.graph.simple;

import main.java.connection.Connection;
import main.java.graph.Graph;
import main.java.graph.GraphType;
import main.java.utils.structures.Edge;

import java.util.*;

public class SimpleGraph implements Graph {
    protected int[][] adjacencyMatrix;
    public HashMap<Integer, Integer> degreeMap = new HashMap<>();
    public HashSet<Edge> edges = new HashSet<>();
    public HashMap<Integer, HashSet<Integer>> neighborMap = new HashMap<>();

    public SimpleGraph() {
        this(0);
    }

    public SimpleGraph(int order) {
        adjacencyMatrix = new int[order][order];

        for (int v = 0; v < order; v++) {
            degreeMap.put(v, 0);
            neighborMap.put(v, new HashSet<>());
        }
    }

    SimpleGraph(SimpleGraph simpleGraph) {
        int[][] adjacencyMatrix = new int[simpleGraph.order()][simpleGraph.order()];
        HashSet<Edge> edges = new HashSet<>();

        for (Edge edge : simpleGraph.edges) {
            adjacencyMatrix[edge.v1][edge.v2] = edge.weight;
            edges.add(new Edge(edge.v1, edge.v2, edge.weight));
        }
        this.adjacencyMatrix = adjacencyMatrix;
        this.edges = edges;

        for (int v = 0; v < simpleGraph.order(); v++) {
            degreeMap.put(v, simpleGraph.degreeOf(v));
            neighborMap.put(v, new HashSet<>(simpleGraph.neighborsOf(v)));
        }
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
    public void addVertex(int v) {
        int[][] adjacencyMatrix = new int[order() + 1][order() + 1];
        HashMap<Integer, Integer> degreeMap = new HashMap<>();
        HashSet<Edge> edges = new HashSet<>();
        HashMap<Integer, HashSet<Integer>> neighborMap = new HashMap<>();
        int i0 = 0;

        degreeMap.put(v, 0);
        neighborMap.put(v, new HashSet<>());

        for (int i = 0; i < order(); i++) {
            if (i == v) {
                i0++;
            }

            int j0 = 0;
            for (int j = 0; j < order(); j++) {
                if (j == v) {
                    j0++;
                }

                int tail = i + i0;
                int head = j + j0;
                int weight = this.adjacencyMatrix[i][j];
                if (weight != 0) {
                    adjacencyMatrix[tail][head] = this.adjacencyMatrix[i][j];
                    edges.add(new Edge(tail, head, weight));
                }
            }

            degreeMap.put(i + i0, degreeOf(i));
            neighborMap.put(i + i0, new HashSet<>(neighborsOf(i)));
        }

        this.adjacencyMatrix = adjacencyMatrix;
        this.degreeMap = degreeMap;
        this.edges = edges;
        this.neighborMap = neighborMap;
    }

    @Override
    public SimpleGraph clone() {
        return new SimpleGraph(this);
    }

    @Override
    public Graph complement() {
        Graph graph = newInstance(order());

        for (int i = 0; i < order(); i++) {
            for (int j = 0; j < order(); j++) {
                if (i != j && getEdgeWeight(i, j) == 0) {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }

    @Override
    public void contract(int v1, int v2) {
        for (Edge edge : new ArrayList<>(edges)) {
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

        return degreeMap.get(v);
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

            return Arrays.deepEquals(adjacencyMatrix, that.getAdjacencyMatrix());
        } else {
            return false;
        }
    }

    @Override
    public int[][] getAdjacencyMatrix() {
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

        for (Edge edge : edges) {
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
            if (edges.contains(edge)) {
                return false;
            }
        }

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
    public int size() {
        return edges.size();
    }

    @Override
    public void swap(int v1, int v2) {
        int[][] adjacencyMatrix = new int[order()][order()];

        for (Edge edge : edges) {
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

            adjacencyMatrix[edge.v1][edge.v2] = edge.weight;
        }
        this.adjacencyMatrix = adjacencyMatrix;

        int degreeOfV1 = degreeOf(v1);
        degreeMap.put(v1, degreeOf(v2));
        degreeMap.put(v2, degreeOfV1);

        HashSet<Integer> neighborsOfV1 = neighborsOf(v1);
        neighborMap.put(v1, neighborsOf(v2));
        neighborMap.put(v2, neighborsOfV1);
    }

    @Override
    public Graph transpose() {
        Graph graph = newInstance(order());

        for (Edge edge : edges) {
            graph.addEdge(edge.getReverse());
        }

        return graph;
    }

    @Override
    public Graph union(Graph graph) {
        HashSet<Edge> unionEdges = new HashSet<>();
        HashSet<Integer> unionVertices = new HashSet<>();

        for (Edge edge : edges) {
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
                for (Edge edge : edges) {
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
        for (Edge edge : edges) {
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