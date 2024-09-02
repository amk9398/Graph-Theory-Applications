package main.java.graph.simple;

import main.java.connection.Connection;
import main.java.graph.Graph;
import main.java.graph.GraphType;

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
        super.addEdge(v1, v2, weight);
        super.addEdge(v2, v1, weight);
    }

    @Override
    public UndirectedGraph clone() {
        return new UndirectedGraph(this);
    }

    @Override
    public Graph complement() {
        Graph graph = newInstance(order());

        for (int i = 0; i < order(); i++) {
            for (int j = i + 1; j < order(); j++) {
                if (getEdgeWeight(i, j) == 0) {
                    graph.addEdge(i, j);
                }
            }
        }

        return graph;
    }

    @Override
    public GraphType getType() {
        return GraphType.UNDIRECTED;
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
    public Graph newInstance(int order) {
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
        super.removeEdge(v1, v2);
        super.removeEdge(v2, v1);
    }

    @Override
    public int size() {
        return super.size() / 2;
    }

    @Override
    public Graph transpose() {
        return clone();
    }

    /**
     * Returns a cotree of the graph. A cotree is the complement of a valid MST.
     *
     * @return The cotree of the graph as an AbstractGraph, or null if the MST is null.
     */
    public Graph cotree() {
        Graph mst = mst();
        return mst == null ? null : mst.complement();
    }

    /**
     * Checks if this given graph is a cotree of the given graph.
     *
     * @param graph The graph to check.
     * @return True if the given graph is a cotree of this graph, false otherwise.
     */
    public boolean isCotreeOf(Graph graph) {
        Graph complement = graph.complement();
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