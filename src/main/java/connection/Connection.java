package main.java.connection;

import main.java.graph.Edge;
import main.java.graph.Graph;
import main.java.utils.structures.UnionFind;

import java.util.ArrayList;

public class Connection {
    /**
     * Applies Kruskal's algorithm to find the Minimum Spanning Tree (MST) of the given graph.
     *
     * @param graph the input graph for which the MST is to be found
     * @return the MST of the input graph
     */
    public static Graph kruskal(Graph graph) {
        Graph mst = new Graph(graph.size());

        ArrayList<Edge> edges = new ArrayList<>(graph.getEdgeList());

        // find edge with minimum weight
        Edge minEdge = null;
        int minWeight = Integer.MAX_VALUE;
        for (Edge edge : edges) {
            if (edge.weight < minWeight) {
                minWeight = edge.weight;
                minEdge = edge;
            }
        }

        // Union-Find structure is used to manage connected components
        UnionFind unionFind = new UnionFind(graph.size());

        while (minEdge != null) {
            mst.setWeight(minEdge.v1, minEdge.v2, graph.weight(minEdge));
            unionFind.union(minEdge.v1, minEdge.v2);
            edges.remove(minEdge);

            minEdge = null;
            minWeight = Integer.MAX_VALUE;

            // Find the next minimum edge that does not form a cycle
            for (Edge edge : edges) {
                if (unionFind.find(edge.v1) != unionFind.find(edge.v2)) {
                    if (edge.weight < minWeight) {
                        minWeight = edge.weight;
                        minEdge = edge;
                    }
                }
            }
        }

        return mst;
    }
}