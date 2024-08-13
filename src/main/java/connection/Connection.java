package main.java.connection;

import main.java.graph.Graph;
import main.java.graph.simple.UndirectedGraph;
import main.java.utils.Utils;
import main.java.utils.structures.Edge;
import main.java.utils.structures.UnionFind;

import java.util.HashSet;
import java.util.Stack;

public class Connection {
    /**
     * Applies Kirchhoff's algorithm to find the number of spanning trees
     * in the given graph.
     *
     * @param graph the input graph
     * @return the number of spanning trees
     */
    public static int kirchhoff(UndirectedGraph graph) {
        if (graph.order() == 0) {
            return 0;
        }

        int[][] laplacian = new int[graph.order()][graph.order()];

        for (int i = 0; i < graph.order(); i++) {
            for (int j = 0; j < graph.order(); j++) {
                if (i == j) {
                    laplacian[i][j] = graph.degreeOf(i);
                } else {
                    laplacian[i][j] = graph.hasEdge(i, j) ? -1 : 0;
                }
            }
        }

        int[][] minor = Utils.removeRowAndColumn(laplacian, 0, 0);

        return Math.abs(Utils.determinant(minor));
    }

    /**
     * Applies Kosaraju's algorithm to find the number of strongly connected
     * components in the given graph
     *
     * @param graph the input graph
     * @return the number of strongly connected components
     */
    public static HashSet<HashSet<Integer>> kosaraju(Graph graph) {
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> visitingOrder = new Stack<>();

        for (int i = 0; i < graph.order(); i++) {
            if (!visited.contains(i)) {
                kosarajuH(graph, i, visited, visitingOrder);
            }
        }

        Graph clone = graph.clone().transpose();
        visited.clear();
        HashSet<HashSet<Integer>> components = new HashSet<>();

        while (!visitingOrder.isEmpty()) {
            int v = visitingOrder.pop();
            if (!visited.contains(v)) {
                HashSet<Integer> visitedBefore = new HashSet<>();

                kosarajuH(clone, v, visited, null);

                HashSet<Integer> component = new HashSet<>(visited);
                component.removeAll(visitedBefore);
                components.add(component);
            }
        }

        return components;
    }

    /**
     * Applies Kruskal's algorithm to find the Minimum Spanning Tree (MST) of the given graph.
     *
     * @param graph the input graph for which the MST is to be found
     * @return the MST of the input graph
     */
    public static UndirectedGraph kruskal(UndirectedGraph graph) {
        UndirectedGraph mst = new UndirectedGraph(graph.order());
        HashSet<Edge> edges = new HashSet<>(graph.getEdges());

        Edge minWeightEdge = null;
        int minWeight = Integer.MAX_VALUE;
        for (Edge edge : edges) {
            if (edge.weight < minWeight) {
                minWeight = edge.weight;
                minWeightEdge = edge;
            }
        }

        UnionFind unionFind = new UnionFind(graph.order());

        while (minWeightEdge != null) {
            mst.addEdge(minWeightEdge.v1, minWeightEdge.v2, graph.getEdgeWeight(minWeightEdge.v1, minWeightEdge.v2));
            unionFind.union(minWeightEdge.v1, minWeightEdge.v2);
            edges.remove(minWeightEdge);

            minWeightEdge = null;
            minWeight = Integer.MAX_VALUE;

            for (Edge edge : edges) {
                if (!unionFind.connected(edge.v1, edge.v2)) {
                    if (edge.weight < minWeight) {
                        minWeight = edge.weight;
                        minWeightEdge = edge;
                    }
                }
            }
        }

        return mst;
    }

    private static void kosarajuH(Graph graph, int v, HashSet<Integer> visited, Stack<Integer> stack) {
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