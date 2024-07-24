package main.java.connection;

import main.java.graph.AbstractGraph;
import main.java.graph.Edge;
import main.java.graph.simple.SimpleGraph;
import main.java.utils.structures.UnionFind;

import java.util.HashSet;
import java.util.Stack;

public class Connection {
    /**
     * Applies Kosaraju's algorithm to find the number of strongly connected
     * components in the given graph
     *
     * @param graph the input graph
     * @return the number of strongly connected components
     */
    public static int kosaraju(AbstractGraph graph) {
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        // perform DFS to populate stack in the visiting order of the vertices
        for (int i = 0; i < graph.order(); i++) {
            if (!visited.contains(i)) {
                kosarajuH(graph, i, visited, stack);
            }
        }

        // reverse the graph
        AbstractGraph clone = graph.clone();
        clone.reverse();
        visited.clear();
        int numComponents = 0;

        // perform DFS on the reverse graph and record num components
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.contains(v)) {
                kosarajuH(clone, v, visited, null);
                numComponents++;
            }
        }

        return numComponents;
    }

    /**
     * Applies Kruskal's algorithm to find the Minimum Spanning Tree (MST) of the given graph.
     *
     * @param graph the input graph for which the MST is to be found
     * @return the MST of the input graph
     */
    public static SimpleGraph kruskal(AbstractGraph graph) {
        SimpleGraph mst = new SimpleGraph(graph.order());
        HashSet<Edge> edges = new HashSet<>(graph.getEdges());

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
        UnionFind unionFind = new UnionFind(graph.order());

        while (minEdge != null) {
            mst.addEdge(minEdge.v1, minEdge.v2, graph.getEdgeWeight(minEdge.v1, minEdge.v2));
            unionFind.union(minEdge.v1, minEdge.v2);
            edges.remove(minEdge);

            minEdge = null;
            minWeight = Integer.MAX_VALUE;

            // find the next minimum edge that does not form a cycle
            for (Edge edge : edges) {
                if (!unionFind.connected(edge.v1, edge.v2)) {
                    if (edge.weight < minWeight) {
                        minWeight = edge.weight;
                        minEdge = edge;
                    }
                }
            }
        }

        return mst;
    }

    private static void kosarajuH(AbstractGraph graph, int v, HashSet<Integer> visited, Stack<Integer> stack) {
        visited.add(v);
        for (Integer i : graph.neighborsOf(v)) {
            if (!visited.contains(i)) {
                kosarajuH(graph, i, visited, stack);
            }
        }

        if (stack != null) {
            stack.push(v);
        }
    }
}