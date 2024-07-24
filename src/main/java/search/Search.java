package main.java.search;

import main.java.graph.AbstractGraph;

import java.util.*;

public class Search {
    /**
     * Performs Breadth-First Search (BFS) to find the shortest path from node v1 to node v2
     * on an unweighted graph.
     *
     * @param graph The graph being searched.
     * @param v1 The starting vertex of the path.
     * @param v2 The target vertex of the path.
     * @return An ArrayList of integers representing the path from v1 to v2, or null if no path is found.
     */
    public static ArrayList<Integer> bfs(AbstractGraph graph, Integer v1, Integer v2) {
        // stores the predecessor of each node to reconstruct the path later
        HashMap<Integer, Integer> predecessorMap = new HashMap<>();

        HashSet<Integer> visited = new HashSet<>();
        ArrayList<Integer> vertexQueue = new ArrayList<>();
        vertexQueue.add(v1);

        int v = -1;
        while (!vertexQueue.isEmpty()) {
            // dequeue the front node
            v = vertexQueue.remove(0);
            if (Objects.equals(v, v2)) {
                break;
            }
            visited.add(v);

            HashSet<Integer> neighbors = graph.neighborsOf(v);
            for (Integer n : neighbors) {
                if (visited.contains(n)) {
                    continue;
                }
                vertexQueue.add(n);
                if (!predecessorMap.containsKey(n)) {
                    predecessorMap.put(n, v);
                }
            }
        }

        // no path found
        if (v != v2) {
            return null;
        }

        // reconstruct the path from v2 to v1 using the predecessor map
        ArrayList<Integer> path = new ArrayList<>();
        Integer prev = v2;
        while (!Objects.equals(prev, v1)) {
            path.add(0, prev);
            prev = predecessorMap.get(prev);
        }
        path.add(0, v1);

        return path;
    }

    /**
     * Performs Depth-First Search (DFS) to find all vertices reachable from 0.
     *
     * @param graph The graph being searched.
     * @return A ArrayList of integers representing the visited vertices in order of visitation.
     */
    public static ArrayList<Integer> dfs(AbstractGraph graph) {
        return dfs(graph, 0);
    }

    /**
     * Performs Depth-First Search (DFS) to find all vertices reachable from v.
     *
     * @param graph The graph being searched.
     * @param v The starting vertex of the path.
     * @return A ArrayList of integers representing the visited vertices in order of visitation.
     */
    public static ArrayList<Integer> dfs(AbstractGraph graph, int v) {
        ArrayList<Integer> visited = new ArrayList<>();

        for (int i = 0; i < graph.order(); i++) {
            if (visited.contains(i)) {
                continue;
            }

            ArrayList<Integer> path = dfsH(graph, v, i, new HashSet<>(), new ArrayList<>());
            for (Integer p : path) {
                if (!visited.contains(p)) {
                    visited.add(p);
                }
            }
        }

        return visited;
    }

    /**
     * Performs Depth-First Search (DFS) to find a path from node v1 to node v2.
     *
     * @param graph The graph being searched.
     * @param v1 The starting vertex of the path.
     * @param v2 The target vertex of the path.
     * @return An ArrayList of integers representing the path from v1 to v2.
     */
    public static ArrayList<Integer> dfs(AbstractGraph graph, int v1, int v2) {
        return dfsH(graph, v1, v2, new HashSet<>(), new ArrayList<>());
    }

    private static ArrayList<Integer> dfsH(AbstractGraph graph, int v1, int v2, HashSet<Integer> visited,
                                           ArrayList<Integer> path) {
        visited.add(v1);
        path.add(v1);

        if (v1 == v2) {
            return path;
        }

        for (Integer v : graph.neighborsOf(v1)) {
            if (!visited.contains(v)) {
                ArrayList<Integer> ret = dfsH(graph, v, v2, visited, path);
                if (!ret.isEmpty()) {
                    return ret;
                }
            }
        }

        // no path found
        path.remove(path.size() - 1);
        return new ArrayList<>();
    }
}