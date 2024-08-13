package main.java.graph;

import main.java.utils.structures.Edge;

import java.util.ArrayList;
import java.util.Random;

/**
 * A builder class for constructing {@link Graph} objects with customizable properties.
 *
 * <p>This class is particularly useful for generating random graphs for testing or simulation purposes.
 *
 * <h3>Example Usage:</h3>
 * <pre>{@code
 * Graph graph = new GraphBuilder()
 *     .graphType(GraphType.SIMPLE)
 *     .order(10)
 *     .size(GraphBuilder.Size.SPARSE)
 *     .weighted(true)
 *     .build();
 * }</pre>
 */
public class GraphBuilder {
    private GraphType graphType = GraphType.DEFAULT_GRAPH_TYPE;
    private int order = 0;
    private Size size = Size.EMPTY;
    private boolean weighted = false;

    public enum Size {
        EMPTY {
            public int getSize(int order, boolean directed) {
                return 0;
            }
        },
        SPARSE {
            public int getSize(int order, boolean directed) {
                int size = (int) Math.min(Math.pow(order, 2), 2 * order);
                return directed ? 2 * size : size;
            }
        },
        MEDIUM {
            public int getSize(int order, boolean directed) {
                return (SPARSE.getSize(order, directed) + DENSE.getSize(order, directed)) / 2;
            }
        },
        DENSE {
            public int getSize(int order, boolean directed) {
                int size = 3 * order * (order - 1) / 8;
                return directed ? 2 * size : size;
            }
        },
        COMPLETE {
            public int getSize (int order, boolean directed) {
                int size = order * (order - 1) / 2;
                return directed ? 2 * size : size;
            }
        };

        public abstract int getSize(int order, boolean directed);
    }

    /**
     * Builds the graph based on the configured parameters.
     *
     * <p>This method creates a new {@link Graph} instance and populates it with edges
     * based on the size and order specified. If the graph is weighted, the edges will
     * have random weights between 1 and 10. Otherwise, all edges will have a weight
     * of 1.
     *
     * @return the constructed {@link Graph} instance
     */
    public Graph build() {
        Graph graph = graphType.newInstance(order);
        Random random = new Random();
        int size = this.size.getSize(order, graph.isDirected());

        ArrayList<Edge> allPossibleEdges = generateAllPossibleEdges();
        for (int i = 0; i < size; i++) {

            int index = random.nextInt(0, allPossibleEdges.size());
            int weight = random.nextInt(1, 11);
            Edge edge = allPossibleEdges.remove(index);

            if (graph.hasEdge(edge)) {
                continue;
            }
            graph.addEdge(edge.v1, edge.v2, weighted ? weight : 1);
        }

        return graph;
    }

    public GraphBuilder graphType(GraphType graphType) {
        this.graphType = graphType;
        return this;
    }

    public GraphBuilder order(int order) {
        this.order = order;
        return this;
    }

    public GraphBuilder size(Size size) {
        this.size = size;
        return this;
    }

    public GraphBuilder weighted(boolean weighted) {
        this.weighted = weighted;
        return this;
    }

    private ArrayList<Edge> generateAllPossibleEdges() {
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                edges.add(new Edge(i, j));
            }
        }

        return edges;
    }
}