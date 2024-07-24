package main.java.walk;

import main.java.graph.AbstractGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class Tour {
    /**
     * Implements Fleury's Algorithm to find an Eulerian tour in a given graph.
     * An Eulerian tour visits every edge of the graph exactly once.
     *
     * @param graph the input graph to find the Eulerian tour for
     * @return an ArrayList of integers representing the Eulerian tour
     */
    public static ArrayList<Integer> fleury(AbstractGraph graph) {
        if (graph.order() == 0) {
            return new ArrayList<>();
        }

        // create a copy of the graph to perform operations without modifying the original
        AbstractGraph copyGraph = graph.clone();

        // find a starting vertex with an odd degree (if any)
        int u = 0;
        for (int v = 0; v < copyGraph.order(); v++) {
            if (copyGraph.degreeOf(v) % 2 == 1) {
                u = v;
                break;
            }
        }

        ArrayList<Integer> tour = new ArrayList<>();
        tour.add(u);

        ArrayList<Integer> neighbors;
        while ((neighbors = new ArrayList<>(copyGraph.neighborsOf(u))).size() != 0) {
            int next = -1;
            for (int v : neighbors) {
                int countBefore = countReachableVertices(copyGraph, u);
                copyGraph.removeEdge(u, v);
                int countAfter = countReachableVertices(copyGraph, v);
                copyGraph.addEdge(u, v);

                // select a non-cut edge
                if (countBefore == countAfter) {
                    next = v;
                    break;
                }
            }

            // if no non-cut edge is found, select next available edge
            if (next == -1) {
                next = neighbors.get(0);
            }

            tour.add(next);
            copyGraph.removeEdge(u, next);
            u = next;
        }

        return tour;
    }

    private static int countReachableVertices(AbstractGraph graph, int u) {
        HashSet<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(u);

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited.contains(vertex)) {
                visited.add(vertex);

                for (int neighbor : graph.neighborsOf(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return visited.size();
    }
}