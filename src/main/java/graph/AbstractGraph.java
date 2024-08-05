package main.java.graph;

import main.java.connection.Connection;
import main.java.distance.Distance;
import main.java.search.Search;
import main.java.walk.Tour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGraph {
    /**
     * Adds an edge to the graph.
     */
    public void addEdge(Edge edge) {
        addEdge(edge.v1, edge.v2, edge.weight);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1     The tail of the edge.
     * @param v2     The head of the edge.
     */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 1);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1     The tail of the edge.
     * @param v2     The head of the edge.
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
     * Determines whether two vertices in a graph are adjacent.
     */
    public boolean adjacent(int v1, int v2) {
        return neighborsOf(v1).contains(v2);
    }

    /**
     * Performs a breadth-first search (BFS) from one vertex to another.
     *
     * @param v1    The starting vertex.
     * @param v2    The target vertex.
     * @return The path from v1 to v2.
     */
    public ArrayList<Integer> bfs(int v1, int v2) {
        return Search.bfs(this, v1, v2);
    }

    /**
     * Finds the center of the graph.
     * The center is the vertex with the lowest eccentricity, or the least
     * max distance to each other vertex.
     */
    public int center() {
        if (order() == 0) {
            return -1;
        }

        int center = 0;
        int minEccentricity = Integer.MAX_VALUE;

        for (int v = 0; v < order(); v++) {
            Integer[] distances = Distance.dijkstra(this, v);
            int maxDistance = Integer.MIN_VALUE;

            for (int i = 0; i < distances.length; i++) {
                if (i != v && distances[i] != null) {
                    maxDistance = Math.max(maxDistance, distances[i]);
                }
            }

            if (maxDistance == Integer.MIN_VALUE) {
                continue;
            }

            if (maxDistance < minEccentricity) {
                minEccentricity = maxDistance;
                center = v;
            }
        }

        return center;
    }

    /**
     * Creates and returns a deep copy of the graph.
     */
    public abstract AbstractGraph clone();

    /**
     * Returns the complement of the graph.
     */
    public abstract AbstractGraph complement();

    /**
     * Determines whether two vertices in a graph are connected.
     */
    public boolean connected(int v1, int v2) {
        return !dfs(v1, v2).isEmpty();
    }

    /**
     * Contracts the edge between two vertices by merging them into a single vertex.
     */
    public void contract(Edge edge) {
        contract(edge.v1, edge.v2);
    }

    /**
     * Contracts the edge between two vertices by merging them into a single vertex.
     */
    public abstract void contract(int v1, int v2);

    /**
     * Returns the degree of a specified vertex.
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
     */
    public int distance(int v1, int v2) {
        return Distance.dijkstra(this, v1, v2);
    }

    /**
     * Returns the adjacency matrix of the graph.
     */
    public abstract int[][] getAdjacencyMatrix();

    /**
     * Get connected components of this graph. The definition of a connected
     * component varies by subgraph type.
     *
     * @return a HashSet of HashSets of vertices, representing the set of components.
     */
    public HashSet<HashSet<Integer>> getComponents() {
        return Connection.kosaraju(this);
    }

    /**
     * Returns a subgraph induced by the specified edges.
     */
    public abstract AbstractGraph getEdgeInducedSubgraph(Set<Edge> edges);

    /**
     * Returns the set of edges in the graph.
     */
    public abstract HashSet<Edge> getEdges();

    /**
     * Returns the weight of a specified edge.
     */
    public int getEdgeWeight(Edge edge) {
        return getEdgeWeight(edge.v1, edge.v2);
    }

    /**
     * Returns the weight of the edge between two vertices.
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
     * Returns the underlying simple graph of a complex graph. This can be
     * found by deleting all loops and multi-edges.
     */
    public abstract AbstractGraph getUnderlyingSimpleSubgraph();

    /**
     * Returns a subgraph induced by the specified vertices.
     */
    public abstract AbstractGraph getVertexInducedSubgraph(Set<Integer> vertices);

    /**
     * Checks if the graph contains a specified edge.
     */
    public boolean hasEdge(Edge edge) {
        return hasEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if the graph contains an edge between two vertices.
     */
    public abstract boolean hasEdge(int v1, int v2);

    /**
     * Computes the intersection of this graph with another graph.
     * The intersection of two graphs is a graph that contains only the edges
     * that are present in both graphs.
     *
     * @param graph The graph to intersect with this graph.
     * @return A new AbstractGraph representing the intersection of this graph and the specified graph.
     */
    public abstract AbstractGraph intersect(AbstractGraph graph);

    /**
     * Checks if the graph is complete.
     */
    public abstract boolean isComplete();

    /**
     * Checks if the graph is connected.
     */
    public abstract boolean isConnected();

    /**
     * Checks if an edge is a cut-edge (bridge).
     */
    public boolean isCutEdge(Edge edge) {
        return isCutEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if an edge between two vertices is a cut-edge (bridge).
     */
    public abstract boolean isCutEdge(int v1, int v2);

    /**
     * Checks if a vertex is a cut-vertex (articulation point).
     */
    public abstract boolean isCutVertex(int v);

    /**
     * Checks if the graph contains cycles.
     */
    public abstract boolean isCyclic();

    /**
     * Checks if the graph is directed.
     */
    public abstract boolean isDirected();

    /**
     * Checks if this graph is edge-disjoint with another graph.
     * Two graphs are edge-disjoint if they do not share any common edges.
     */
    public abstract boolean isEdgeDisjoint(AbstractGraph graph);

    /**
     * Checks if the graph is empty (contains no edges).
     */
    public abstract boolean isEmpty();

    /**
     * Checks if the graph is Eulerian.
     */
    public abstract boolean isEulerian();

    /**
     * Checks if the graph is simple (contains no loops or multiple edges).
     */
    public abstract boolean isSimple();

    /**
     * Checks if this graph is a spanning subgraph of another graph.
     * A spanning subgraph is a subgraph that contains all the vertices of the original graph.
     */
    public boolean isSpanningSubgraphOf(AbstractGraph graph) {
        return isSubgraphOf(graph) && graph.order() == order();
    }

    /**
     * Checks if this graph is a subgraph of another graph.
     * A subgraph is a graph formed from a subset of the vertices and edges of another graph.
     */
    public abstract boolean isSubgraphOf(AbstractGraph graph);

    /**
     * Checks if the graph is a tree.
     * A tree is a connected, acyclic graph.
     */
    public abstract boolean isTree();

    /**
     * Returns the maximum degree of any vertex in the graph.
     */
    public abstract int maxDegree();

    /**
     * Returns the minimum degree of any vertex in the graph.
     */
    public abstract int minDegree();

    /**
     * Creates a subgraph by removing a specific edge from this graph.
     */
    public AbstractGraph minus(Edge edge) {
        HashSet<Edge> minusEdges = new HashSet<>(getEdges());
        minusEdges.remove(edge);

        return getEdgeInducedSubgraph(minusEdges);
    }

    /**
     * Creates a subgraph by removing a set of edges from this graph.
     */
    public AbstractGraph minus(HashSet<Edge> edges) {
        HashSet<Edge> minusEdges = new HashSet<>(getEdges());
        minusEdges.removeAll(edges);

        return getEdgeInducedSubgraph(minusEdges);
    }

    /**
     * Returns the neighbors of a specified vertex.
     */
    public abstract HashSet<Integer> neighborsOf(int v);

    /**
     * Returns the number of connected components in the graph.
     */
    public abstract int numComponents();

    /**
     * Returns the number of vertices in the graph.
     */
    public abstract int order();

    /**
     * Removes an edge from the graph.
     */
    public void removeEdge(Edge edge) {
        removeEdge(edge.v1, edge.v2);
    }

    /**
     * Removes an edge between two vertices from the graph.
     */
    public abstract void removeEdge(int v1, int v2);

    /**
     * Removes a vertex from the graph.
     */
    public abstract void removeVertex(int v);

    /**
     * Returns the number of edges in the graph.
     */
    public abstract int size();

    /**
     * Computes the transpose of this graph.
     * The transpose of a graph is obtained by reversing the direction of all edges.
     * For an undirected graph, the transpose is the same as the original graph.
     */
    public abstract AbstractGraph transpose();

    /**
     * Computes the union of this graph with another graph.
     * The union of two graphs contains all the vertices from both graphs and all the edges
     * that are present in either of the graphs.
     */
    public abstract AbstractGraph union(AbstractGraph graph);

    /**
     * This is a protected method implemented by non-abstract subgraphs to return a new
     * graph of the correct type.
     */
    protected abstract AbstractGraph newGraph(int order);
}