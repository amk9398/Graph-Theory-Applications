package main.java.distance;

import main.java.graph.Graph;

import java.util.HashSet;

public class Distance {
    /**
     * Computes the shortest path distances from the start vertex to all other vertices
     * in the given graph using Dijkstra's Algorithm.
     *
     * @param graph the graph on which to run Dijkstra's algorithm.
     * @param start the starting vertex from which to calculate the shortest path distances.
     * @return an array where the value at index {@code v} is the shortest distance from the start vertex to vertex {@code v}.
     */
    public static Integer[] dijkstra(Graph graph, int start) {
        Integer[] distances = new Integer[graph.order()];
        HashSet<Integer> unvisited = new HashSet<>();

        for (int v = 0; v < graph.order(); v++) {
            distances[v] = null;
            unvisited.add(v);
        }
        distances[start] = 0;
        unvisited.remove(start);

        int current = start;
        for (int i = 0; i < graph.order() - 1; i++) {
            int minVertex = current;
            int minDist = Integer.MAX_VALUE;

            for (int vertex : unvisited) {
                Integer dist = distances[vertex];
                int weight = graph.getEdgeWeight(current, vertex);
                if (weight != 0 && (dist == null || distances[current] + weight < dist)) {
                    dist = distances[current] + weight;
                }
                distances[vertex] = dist;

                if (dist != null && dist < minDist) {
                    minDist = dist;
                    minVertex = vertex;
                }
            }

            current = minVertex;
            unvisited.remove(current);
        }
        
        return distances;
    }

    /**
     * Computes the shortest path distance from the start vertex to the end vertex
     * in the given graph using Dijkstra's Algorithm.
     *
     * @param graph the graph on which to run Dijkstra's algorithm.
     * @param start the starting vertex from which to calculate the shortest path distance.
     * @param end the ending vertex to which the shortest path distance is calculated.
     * @return the shortest distance from the start vertex to the end vertex.
     */
    public static Integer dijkstra(Graph graph, int start, int end) {
        return dijkstra(graph, start)[end];
    }
}