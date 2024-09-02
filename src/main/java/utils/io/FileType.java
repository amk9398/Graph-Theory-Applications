package main.java.utils.io;

import main.java.graph.Graph;
import main.java.graph.GraphType;
import main.java.utils.structures.Edge;

import java.util.*;

public enum FileType {
    ADJACENCY_LIST {
        public Optional<Graph> read(String[] data, GraphType graphType) {
            if (data.length == 0) {
                return Optional.empty();
            }

            HashMap<Integer, HashSet<Integer>> neighborMap = new HashMap<>();
            int order;

            try {
                order = Integer.parseInt(data[0].trim());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }

            for (int row = 1; row < data.length; row++) {
                HashSet<Integer> set = new HashSet<>();
                Optional<int[]> neighbors = FileType.parseIntArray(data[row]);

                if (neighbors.isEmpty() || neighbors.get().length == 0) {
                    continue;
                }

                int v = neighbors.get()[0];
                for (int i = 1; i < neighbors.get().length; i++) {
                    set.add(neighbors.get()[i]);
                }

                neighborMap.put(v, set);
            }

            return read(neighborMap, order, graphType);
        }

        public Optional<Graph> read(HashMap<Integer, HashSet<Integer>> data, int order, GraphType graphType) {
            return graphType.fromAdjacencyList(data, order);
        }

        public String toString(Graph graph) {
            StringBuilder builder = new StringBuilder();
            builder.append(graph.order());

            for (int v = 0; v < graph.order(); v++) {
                builder.append("\n");

                HashSet<Integer> neighbors = graph.neighborsOf(v);

                StringBuilder rowBuilder = new StringBuilder();
                rowBuilder.append(v);
                for (Integer n : neighbors) {
                    rowBuilder.append(", ");
                    rowBuilder.append(n);
                }

                builder.append(rowBuilder);
            }

            return builder.toString();
        }
    },

    ADJACENCY_MATRIX {
        public Optional<Graph> read(String[] data, GraphType graphType) {
            int order = data.length;
            int[][] adjacencyMatrix = new int[order][order];

            for (int row = 0; row < order; row++) {
                String[] strCol = data[row].split(",");
                int[] cols = new int[strCol.length];

                for (int col = 0; col < cols.length; col++) {
                    try {
                        adjacencyMatrix[row][col] = Integer.parseInt(strCol[col].trim());
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                }
            }

            return read(adjacencyMatrix, graphType);
        }

        public Optional<Graph> read(int[][] adjacencyMatrix, GraphType graphType) {
           return graphType.fromAdjacencyMatrix(adjacencyMatrix);
        }

        public String toString(Graph graph) {
            int[][] adjacencyMatrix = graph.getAdjacencyMatrix();
            StringBuilder builder = new StringBuilder();

            for (int[] row : adjacencyMatrix) {
                if (!builder.isEmpty()) {
                    builder.append("\n");
                }

                String rowString = Arrays.toString(row);
                builder.append(rowString, 1, rowString.length() - 1);
            }

            return builder.toString();

        }
    },

    EDGE_LIST {
        public Optional<Graph> read(String[] data, GraphType graphType) {
            if (data.length == 0) {
                return Optional.empty();
            }

            Edge[] edgeList = new Edge[data.length - 1];

            int order;
            try {
                order = Integer.parseInt(data[0].trim());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }

            for (int row = 1; row < data.length; row++) {
                Optional<Edge> edge = Edge.parse(data[row]);
                if (edge.isEmpty()) {
                    return Optional.empty();
                }

               edgeList[row - 1] = edge.get();
            }

            return read(edgeList, order, graphType);
        }

        public Optional<Graph> read(Edge[] edgeList, int order, GraphType graphType) {
            return graphType.fromEdgeList(edgeList, order);
        }

        public String toString(Graph graph) {
            Set<Edge> edges = graph.getEdges();
            StringBuilder builder = new StringBuilder();
            builder.append(graph.order());

            for (Edge edge : edges) {
                builder.append("\n");

                if (edge.weight == 0) {
                    continue;
                }

                builder.append(edge.v1).append(", ").append(edge.v2);

                if (edge.weight != 1) {
                    builder.append(", ").append(edge.weight);
                }
            }

            return builder.toString();
        }
    };

    public static Optional<FileType> fromString(String fileType) {
        return switch (fileType.toLowerCase()) {
            case "adjacency_list", "alist" -> Optional.of(ADJACENCY_LIST);
            case "adjacency_matrix", "amatrix" -> Optional.of(ADJACENCY_MATRIX);
            case "edge_list", "elist" -> Optional.of(EDGE_LIST);
            default -> Optional.empty();
        };
    }

    public abstract Optional<Graph> read(String[] data, GraphType graphType);

    public abstract String toString(Graph graph);

    private static Optional<int[]> parseIntArray(String row) {
        String[] split = row.split(",");
        int[] arr = new int[split.length];

        for (int i = 0; i < arr.length; i++) {
            try {
                arr[i] = Integer.parseInt(split[i].trim());
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }

        return Optional.of(arr);
    }
}