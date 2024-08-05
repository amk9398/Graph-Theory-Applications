package main.java.graph.simple;

import main.java.connection.Connection;
import main.java.graph.AbstractGraph;
import main.java.graph.Edge;

import java.util.HashSet;

public class UndirectedGraph extends SimpleGraph {
    public UndirectedGraph(int order) {
        super(order);
    }

    private UndirectedGraph(UndirectedGraph undirectedGraph) {
        super(undirectedGraph);
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (!isValidEdge(v1, v2) || weight == getEdgeWeight(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = weight;
        adjacencyMatrix[v2][v1] = weight;
        degreeMap.put(v1, degreeOf(v1) + 1);
        degreeMap.put(v2, degreeOf(v2) + 1);
        edges.add(new Edge(v1, v2, weight));
        neighborMap.get(v1).add(v2);
        neighborMap.get(v2).add(v1);
    }

    @Override
    public UndirectedGraph clone() {
        return new UndirectedGraph(this);
    }

    @Override
    public boolean isComplete() {
        return size() == order() * (order() - 1) / 2;
    }

    @Override
    public boolean isConnected() {
        return dfs().size() == order();
    }

    @Override
    public boolean isCyclic() {
        HashSet<Integer> visited = new HashSet<>();

        for (int v = 0; v < order(); v++) {
            if (!visited.contains(v)) {
                if (isCyclicH(v, -1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public AbstractGraph newGraph(int order) {
        return new UndirectedGraph(order);
    }

    @Override
    public int numComponents() {
        int numComponents = 0;
        HashSet<Integer> visited = new HashSet<>();

        for (int v = 0; v < order(); v++) {
            if (!visited.contains(v)) {
                visited.addAll(dfs(v));
                numComponents++;
            }
        }

        return numComponents;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!isValidEdge(v1, v2) || !hasEdge(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = 0;
        adjacencyMatrix[v2][v1] = 0;
        degreeMap.put(v1, degreeOf(v1) - 1);
        degreeMap.put(v2, degreeOf(v2) - 1);
        edges.remove(new Edge(v1, v2));
        edges.remove(new Edge(v2, v1));
        neighborMap.get(v1).remove(v2);
        neighborMap.get(v2).remove(v1);
    }

    @Override
    public void removeVertex(int v) {
        if (order() == 0) {
            return;
        }

        UndirectedGraph undirectedGraph = new UndirectedGraph(order() - 1);
        for (Edge edge : getEdges()) {
            if (edge.v1 != v && edge.v2 != v) {
                int i0 = edge.v1 < v ? edge.v1 : edge.v1 - 1;
                int j0 = edge.v2 < v ? edge.v2 : edge.v2 - 1;
                undirectedGraph.addEdge(i0, j0, getEdgeWeight(edge));
            }
        }

        adjacencyMatrix = undirectedGraph.adjacencyMatrix;
        degreeMap = undirectedGraph.degreeMap;
        edges = undirectedGraph.edges;
        neighborMap = undirectedGraph.neighborMap;
    }

    @Override
    public AbstractGraph transpose() {
        return clone();
    }

    /**
     * Returns a cotree of the graph. A cotree is the complement of a valid MST.
     *
     * @return The cotree of the graph as an AbstractGraph, or null if the MST is null.
     */
    public AbstractGraph cotree() {
        AbstractGraph mst = mst();
        return mst == null ? null : mst.complement();
    }

    /**
     * Checks if this given graph is a cotree of the given graph.
     *
     * @param graph The graph to check.
     * @return True if the given graph is a cotree of this graph, false otherwise.
     */
    public boolean isCotreeOf(AbstractGraph graph) {
        AbstractGraph complement = graph.complement();
        return complement.isTree() && complement.order() == order();
    }

    /**
     * Returns the minimum spanning tree (MST) of the graph.
     *
     * @return The MST as a SimpleGraph, or null if there is no valid MST.
     */
    public UndirectedGraph mst() {
        UndirectedGraph mst = Connection.kruskal(this);

        if (mst.size() != mst.order() - 1) {
            return null;
        }

        return mst;
    }

    /**
     * Calculates the number of spanning trees in the graph using Kirchhoff's algorithm.
     *
     * @return The number of spanning trees in the graph.
     */
    public int numSpanningTrees() {
        return Connection.kirchhoff(this);
    }

    private boolean isCyclicH(int v, int parent, HashSet<Integer> visited) {
        visited.add(v);

        for (Integer i : neighborsOf(v)) {
            if (!visited.contains(i)) {
                if (isCyclicH(i, v, visited)) {
                    return true;
                }
            } else if (i != parent) {
                return true;
            }
        }

        return false;
    }
}