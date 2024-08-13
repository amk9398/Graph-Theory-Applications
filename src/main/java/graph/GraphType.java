package main.java.graph;

import main.java.graph.simple.SimpleGraph;
import main.java.graph.simple.UndirectedGraph;
import main.java.utils.structures.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public enum GraphType {
    GRAPH {
        public Graph newInstance(int order) {
            return null;
        }
    },
    SIMPLE {
        public Graph newInstance(int order) {
            return new SimpleGraph(order);
        }
    },
    UNDIRECTED {
        public Graph newInstance(int order) {
            return new UndirectedGraph(order);
        }
    };

    public static final GraphType DEFAULT_GRAPH_TYPE = SIMPLE;

    public Optional<Graph> fromAdjacencyList(HashMap<Integer, HashSet<Integer>> adjacencyList, int order) {
        Graph graph = newInstance(order);
        graph.init(adjacencyList);

        return Optional.of(graph);
    }

    public Optional<Graph> fromAdjacencyMatrix(int[][] adjacencyMatrix) {
        Graph graph = newInstance(adjacencyMatrix.length);
        graph.init(adjacencyMatrix);

        return Optional.of(graph);
    }

    public Optional<Graph> fromEdgeList(Edge[] edgeList, int order) {
        Graph graph = newInstance(order);
        graph.init(edgeList);

        return Optional.of(graph);
    }

    public Graph newInstance() {
        return newInstance(0);
    }

    public abstract Graph newInstance(int order);

    public static GraphType fromString(String graphType) {
        return switch (graphType.toLowerCase()) {
            case "graph" -> GRAPH;
            case "simple" -> SIMPLE;
            case "undirected" -> UNDIRECTED;
            default -> DEFAULT_GRAPH_TYPE;
        };
    }
}
