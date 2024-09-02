package main.java.graph;

import main.java.connection.Connection;
import main.java.distance.Distance;
import main.java.search.Search;
import main.java.utils.io.FileType;
import main.java.utils.io.GraphReader;
import main.java.utils.io.GraphWriter;
import main.java.utils.structures.Edge;
import main.java.walk.Tour;

import java.util.*;

public interface Graph {
    /**
     * Adds an edge to the graph.
     */
    default void addEdge(Edge edge) {
        addEdge(edge.v1, edge.v2, edge.weight);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1     The tail of the edge.
     * @param v2     The head of the edge.
     */
    default void addEdge(int v1, int v2) {
        addEdge(v1, v2, 1);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param v1     The tail of the edge.
     * @param v2     The head of the edge.
     * @param weight The weight of the edge.
     */
    void addEdge(int v1, int v2, int weight);

    /**
     * Adds a vertex to the graph.
     *
     * @return The index of the newly added vertex.
     */
    int addVertex();

    /**
     * Adds a vertex to the graph at the specified location.
     */
    void addVertex(int v);

    /**
     * Determines whether two vertices in a graph are adjacent.
     */
    default boolean adjacent(int v1, int v2) {
        return neighborsOf(v1).contains(v2);
    }

    /**
     * Performs a breadth-first search (BFS) from one vertex to another.
     *
     * @param v1    The starting vertex.
     * @param v2    The target vertex.
     * @return The path from v1 to v2.
     */
    default ArrayList<Integer> bfs(int v1, int v2) {
        return Search.bfs(this, v1, v2);
    }

    /**
     * Finds the center of the graph.
     * The center is the vertex with the lowest eccentricity, or the least
     * max distance to each other vertex.
     */
    default int center() {
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
    Graph clone();

    /**
     * Returns the complement of the graph.
     */
    Graph complement();

    /**
     * Determines whether two vertices in a graph are connected.
     */
    default boolean connected(int v1, int v2) {
        return !dfs(v1, v2).isEmpty();
    }

    /**
     * Contracts the edge between two vertices by merging them into a single vertex.
     */
    default void contract(Edge edge) {
        contract(edge.v1, edge.v2);
    }

    /**
     * Contracts the edge between two vertices by merging them into a single vertex.
     */
    void contract(int v1, int v2);

    /**
     * Returns the degree of a specified vertex.
     */
    int degreeOf(int v);

    /**
     * Performs a depth-first search (DFS) on the graph starting from vertex 0.
     *
     * @return A list of vertices visited in DFS order.
     */
    default ArrayList<Integer> dfs() {
        return Search.dfs(this);
    }

    /**
     * Performs a depth-first search (DFS) starting from a specific vertex.
     *
     * @param v The starting vertex.
     * @return A list of vertices visited in DFS order.
     */
    default ArrayList<Integer> dfs(int v) {
        return Search.dfs(this, v);
    }

    /**
     * Performs a depth-first search (DFS) from one vertex to another.
     *
     * @param v1 The starting vertex.
     * @param v2 The target vertex.
     * @return A list of vertices visited in DFS order.
     */
    default ArrayList<Integer> dfs(int v1, int v2) {
        return Search.dfs(this, v1, v2);
    }

    /**
     * Calculates the shortest distance between two vertices.
     */
    default int distance(int v1, int v2) {
        Integer distance = Distance.dijkstra(this, v1, v2);
        if (distance == null) {
            return -1;
        }

        return distance;
    }

    /**
     * Returns the adjacency matrix of the graph.
     */
    int[][] getAdjacencyMatrix();

    /**
     * Get connected components of this graph. The definition of a connected
     * component varies by subgraph type.
     *
     * @return a HashSet of HashSets of vertices, representing the set of components.
     */
    default HashSet<HashSet<Integer>> getComponents() {
        return Connection.kosaraju(this);
    }

    /**
     * Returns a subgraph induced by the specified edges.
     */
    Graph getEdgeInducedSubgraph(Set<Edge> edges);

    /**
     * Returns the set of edges in the graph.
     */
    Set<Edge> getEdges();

    /**
     * Returns the weight of a specified edge.
     */
    default int getEdgeWeight(Edge edge) {
        return getEdgeWeight(edge.v1, edge.v2);
    }

    /**
     * Returns the weight of the edge between two vertices.
     */
    int getEdgeWeight(int v1, int v2);

    /**
     * Returns a maximum tour of the graph if it is Eulerian.
     *
     * @return A list of vertices in the maximum tour, or null if the graph is not Eulerian.
     */
    default ArrayList<Integer> getEulerTour() {
        if (!isEulerian()) {
            return null;
        }

        return Tour.fleury(this);
    }

    GraphType getType();

    /**
     * Returns the underlying simple graph of a complex graph. This can be
     * found by deleting all loops and multi-edges.
     */
    Graph getUnderlyingSimpleSubgraph();

    /**
     * Returns a subgraph induced by the specified vertices.
     */
    Graph getVertexInducedSubgraph(Set<Integer> vertices);

    /**
     * Checks if the graph contains a specified edge.
     */
    default boolean hasEdge(Edge edge) {
        return hasEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if the graph contains an edge between two vertices.
     */
    boolean hasEdge(int v1, int v2);

    void init(HashMap<Integer, HashSet<Integer>> adjacencyList);

    void init(int[][] adjacencyMatrix);

    void init(Edge[] edgeList);

    /**
     * Computes the intersection of this graph with another graph.
     * The intersection of two graphs is a graph that contains only the edges
     * that are present in both graphs.
     *
     * @param graph The graph to intersect with this graph.
     * @return A new AbstractGraph representing the intersection of this graph and the specified graph.
     */
    Graph intersect(Graph graph);

    /**
     * Checks if the graph is complete.
     */
    boolean isComplete();

    /**
     * Checks if the graph is connected.
     */
    boolean isConnected();

    /**
     * Checks if an edge is a cut-edge (bridge).
     */
    default boolean isCutEdge(Edge edge) {
        return isCutEdge(edge.v1, edge.v2);
    }

    /**
     * Checks if an edge between two vertices is a cut-edge (bridge).
     */
    boolean isCutEdge(int v1, int v2);

    /**
     * Checks if a vertex is a cut-vertex (articulation point).
     */
    boolean isCutVertex(int v);

    /**
     * Checks if the graph contains cycles.
     */
    boolean isCyclic();

    /**
     * Checks if the graph is directed.
     */
    boolean isDirected();

    /**
     * Checks if this graph is edge-disjoint with another graph.
     * Two graphs are edge-disjoint if they do not share any common edges.
     */
    boolean isEdgeDisjoint(Graph graph);

    /**
     * Checks if the graph is empty (contains no edges).
     */
    boolean isEmpty();

    /**
     * Checks if the graph is Eulerian.
     */
    boolean isEulerian();

    /**
     * Checks if the graph is simple (contains no loops or multiple edges).
     */
    boolean isSimple();

    /**
     * Checks if this graph is a spanning subgraph of another graph.
     * A spanning subgraph is a subgraph that contains all the vertices of the original graph.
     */
    default boolean isSpanningSubgraphOf(Graph graph) {
        return isSubgraphOf(graph) && graph.order() == order();
    }

    /**
     * Checks if this graph is a subgraph of another graph.
     * A subgraph is a graph formed from a subset of the vertices and edges of another graph.
     */
    boolean isSubgraphOf(Graph graph);

    /**
     * Checks if the graph is a tree.
     * A tree is a connected, acyclic graph.
     */
    boolean isTree();

    /**
     * Returns the maximum degree of any vertex in the graph.
     */
    int maxDegree();

    /**
     * Returns the minimum degree of any vertex in the graph.
     */
    int minDegree();

    /**
     * Creates a subgraph by removing a specific edge from this graph.
     */
    default Graph minus(Edge edge) {
        HashSet<Edge> minusEdges = new HashSet<>(getEdges());
        minusEdges.remove(edge);

        return getEdgeInducedSubgraph(minusEdges);
    }

    /**
     * Creates a subgraph by removing a set of edges from this graph.
     */
    default Graph minus(HashSet<Edge> edges) {
        HashSet<Edge> minusEdges = new HashSet<>(getEdges());
        minusEdges.removeAll(edges);

        return getEdgeInducedSubgraph(minusEdges);
    }

    /**
     * Returns the neighbors of a specified vertex.
     */
    HashSet<Integer> neighborsOf(int v);

    /**
     * Create a new graph instance of the same type.
     */
    default Graph newInstance(int order) {
        return getType().newInstance(order);
    }

    /**
     * Returns the number of connected components in the graph.
     */
    int numComponents();

    /**
     * Returns the number of vertices in the graph.
     */
    int order();

    /**
     * Attempts to read a graph from the specified file.
     *
     * @param filename path to the graph file
     * @return an {@link Optional} containing the {@link Graph} if the file is successfully read;
     *         an empty {@link Optional} if the file cannot be read
     */
    static Optional<Graph> read(String filename) {
        return GraphReader.read(filename);
    }

    /**
     * Removes an edge from the graph.
     */
    default void removeEdge(Edge edge) {
        removeEdge(edge.v1, edge.v2);
    }

    /**
     * Removes an edge between two vertices from the graph.
     */
    void removeEdge(int v1, int v2);

    /**
     * Removes a vertex from the graph.
     */
    void removeVertex(int v);

    /**
     * Returns the number of edges in the graph.
     */
    int size();

    /**
     * Swaps the IDs/indices of two vertices.
     */
    void swap(int v1, int v2);

    /**
     * Computes the transpose of this graph.
     * The transpose of a graph is obtained by reversing the direction of all edges.
     * For an undirected graph, the transpose is the same as the original graph.
     */
    Graph transpose();

    /**
     * Computes the union of this graph with another graph.
     * The union of two graphs contains all the vertices from both graphs and all the edges
     * that are present in either of the graphs.
     */
    Graph union(Graph graph);

    /**
     * Writes this graph to a file with the specified file type and path.
     *
     * @param fileType the type of the file to which the graph should be written (adjacency
     *                 list, adjacency matrix, edge list, etc.)
     * @param path the path where the graph file will be saved
     */
    default void write(FileType fileType, String path) {
        GraphWriter.write(this, fileType, path);
    }
}