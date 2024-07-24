package main.java.graph;

import main.java.connection.Connection;
import main.java.distance.Distance;
import main.java.graph.simple.SimpleGraph;
import main.java.search.Search;
import main.java.walk.Tour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGraph {
    /**
     * Adds an edge to the graph.
     *
     * @param edge The edge to be added.
     */
    public void addEdge(Edge edge) {
        addEdge(edge.v1, edge.v2, edge.weight);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 1);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1     The first vertex.
     * @param v2     The second vertex.
     * @param weight The weight of the edge.
     */
    public abstract void addEdge(int v1, int v2, int weight);

    /**
     * Adds a vertex to the graph.
     *
     * @return The index of the newly added vertex.
     */
    public abstract int addVertex();

    /**
     * Performs a breadth-first search (BFS) from one vertex to another.
     *
     * @param v1 The starting vertex.
     * @param v2 The target vertex.
     * @return The path from v1 to v2.
     */
    public ArrayList<Integer> bfs(int v1, int v2) {
        return Search.bfs(this, v1, v2);
    }

    /**
     * Creates and returns a deep copy of the graph.
     *
     * @return A cloned copy of the graph.
     */
    public abstract AbstractGraph clone();

    /**
     * Returns the complement of the graph.
     *
     * @return The complement graph.
     */
    public abstract AbstractGraph complement();

    /**
     * Returns the degree of a specified vertex.
     *
     * @param v The vertex.
     * @return The degree of the vertex.
     */
    public abstract int degreeOf(int v);

    /**
     * Performs a depth-first search (DFS) on the graph starting from vertex 0.
     *
     * @return A list of vertices visited in DFS order.
     */
    public ArrayList<Integer> dfs() {
        return Search.dfs(this);
    }

    /**
     * Performs a depth-first search (DFS) starting from a specific vertex.
     *
     * @param v The starting vertex.
     * @return A list of vertices visited in DFS order.
     */
    public ArrayList<Integer> dfs(int v) {
        return Search.dfs(this, v);
    }

    /**
     * Performs a depth-first search (DFS) from one vertex to another.
     *
     * @param v1 The starting vertex.
     * @param v2 The target vertex.
     * @return A list of vertices visited in DFS order.
     */
    public ArrayList<Integer> dfs(int v1, int v2) {
        return Search.dfs(this, v1, v2);
    }

    /**
     * Calculates the shortest distance between two vertices.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return The shortest distance between the vertices.
     */
    public int distance(int v1, int v2) {
        return Distance.dijkstra(this, v1, v2);
    }

    /**
     * Returns the adjacency matrix of the graph.
     *
     * @return The adjacency matrix.
     */
    public abstract int[][] getAdjacencyMatrix();

    /**
     * Returns a subgraph induced by the specified edges.
     *
     * @param edges The set of edges.
     * @return The edge-induced subgraph.
     */
    public abstract AbstractGraph getEdgeInducedSubgraph(Set<Edge> edges);

    /**
     * Returns the set of edges in the graph.
     *
     * @return The set of edges.
     */
    public abstract HashSet<Edge> getEdges();

    /**
     * Returns the weight of a specified edge.
     *
     * @param edge The edge.
     * @return The weight of the edge.
     */
    public int getEdgeWeight(Edge edge) {
        return getEdgeWeight(edge.v1, edge.v2);
    }

    /**
     * Returns the weight of the edge between two vertices.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return The weight of the edge.
     */
    public abstract int getEdgeWeight(int v1, int v2);

    /**
     * Returns a maximum tour of the graph if it is Eulerian.
     *
     * @return A list of vertices in the maximum tour, or null if the graph is not Eulerian.
     */
    public ArrayList<Integer> getEulerTour() {
        if (!isEulerian()) {
            return null;
        }

        return Tour.fleury(this);
    }

    /**
     * Returns a subgraph induced by the specified vertices.
     *
     * @param vertices The set of vertices.
     * @return The vertex-induced subgraph.
     */
    public abstract AbstractGraph getVertexInducedSubgraph(Set<Integer> vertices);

    /**
     * Checks if the graph contains a specified edge.
     *
     * @param edge The edge.
     * @return True if the edge exists, otherwise false.
     */
    public boolean hasEdge(Edge edge) {
        return hasEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if the graph contains an edge between two vertices.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return True if the edge exists, otherwise false.
     */
    public abstract boolean hasEdge(int v1, int v2);

    /**
     * Checks if the graph is complete.
     *
     * @return True if the graph is complete, otherwise false.
     */
    public abstract boolean isComplete();

    /**
     * Checks if the graph is connected.
     *
     * @return True if the graph is connected, otherwise false.
     */
    public abstract boolean isConnected();

    /**
     * Checks if an edge is a cut-edge (bridge).
     *
     * @param edge The edge.
     * @return True if the edge is a cut-edge, otherwise false.
     */
    public boolean isCutEdge(Edge edge) {
        return isCutEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if an edge between two vertices is a cut-edge (bridge).
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     * @return True if the edge is a cut-edge, otherwise false.
     */
    public abstract boolean isCutEdge(int v1, int v2);

    /**
     * Checks if a vertex is a cut-vertex (articulation point).
     *
     * @param v The vertex.
     * @return True if the vertex is a cut-vertex, otherwise false.
     */
    public abstract boolean isCutVertex(int v);

    /**
     * Checks if the graph contains cycles.
     *
     * @return True if the graph is cyclic, otherwise false.
     */
    public abstract boolean isCyclic();

    /**
     * Checks if the graph is directed.
     *
     * @return True if the graph is directed, otherwise false.
     */
    public abstract boolean isDirected();

    /**
     * Checks if the graph is empty (contains no vertices or edges).
     *
     * @return True if the graph is empty, otherwise false.
     */
    public abstract boolean isEmpty();

    /**
     * Checks if the graph is Eulerian.
     *
     * @return True if the graph is Eulerian, otherwise false.
     */
    public abstract boolean isEulerian();

    /**
     * Checks if the graph is identical to another graph.
     *
     * @param graph The other graph.
     * @return True if the graphs are identical, otherwise false.
     */
    public abstract boolean isIdentical(AbstractGraph graph);

    /**
     * Checks if the graph is simple (contains no loops or multiple edges).
     *
     * @return True if the graph is simple, otherwise false.
     */
    public abstract boolean isSimple();

    /**
     * Checks if the graph is a tree.
     *
     * @return True if the graph is a tree, otherwise false.
     */
    public abstract boolean isTree();

    /**
     * Returns the maximum degree of any vertex in the graph.
     *
     * @return The maximum degree.
     */
    public abstract int maxDegree();

    /**
     * Returns the minimum degree of any vertex in the graph.
     *
     * @return The minimum degree.
     */
    public abstract int minDegree();

    /**
     * Returns the minimum spanning tree (MST) of the graph.
     *
     * @return The MST as a SimpleGraph, or null if there is no valid MST.
     */
    public SimpleGraph mst() {
        SimpleGraph mst = Connection.kruskal(this);

        if (mst.size() != mst.order() - 1) {
            return null;
        }

        return mst;
    }

    /**
     * Returns the neighbors of a specified vertex.
     *
     * @param v The vertex.
     * @return The set of neighboring vertices.
     */
    public abstract HashSet<Integer> neighborsOf(int v);

    /**
     * Returns the number of connected components in the graph.
     *
     * @return The number of components.
     */
    public abstract int numComponents();

    /**
     * Returns the number of vertices in the graph.
     *
     * @return The order of the graph.
     */
    public abstract int order();

    /**
     * Removes an edge from the graph.
     *
     * @param edge The edge to be removed.
     */
    public void removeEdge(Edge edge) {
        removeEdge(edge.v1, edge.v2);
    }

    /**
     * Removes an edge between two vertices from the graph.
     *
     * @param v1 The first vertex.
     * @param v2 The second vertex.
     */
    public abstract void removeEdge(int v1, int v2);

    /**
     * Removes a vertex from the graph.
     *
     * @param v The vertex to be removed.
     */
    public abstract void removeVertex(int v);

    /**
     * Reverses the direction of all edges in the graph (for directed graphs).
     */
    public abstract void reverse();

    /**
     * Returns the number of edges in the graph.
     *
     * @return The size of the graph.
     */
    public abstract int size();
}