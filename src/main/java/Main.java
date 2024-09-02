package main.java;

import main.java.graph.GraphBuilder;
import main.java.graph.GraphType;
import main.java.utils.io.FileType;

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i < 6; i++) {
            System.out.println("== starting i=" + i + " ==");
            int order = (int) Math.pow(10, i);
            writeGraph(order, GraphBuilder.Size.EMPTY);
            writeGraph(order, GraphBuilder.Size.SPARSE);
            writeGraph(order, GraphBuilder.Size.MEDIUM);
            writeGraph(order, GraphBuilder.Size.DENSE);
            writeGraph(order, GraphBuilder.Size.COMPLETE);
        }
    }

    private static void writeGraph(int order, GraphBuilder.Size size) {
        new GraphBuilder()
                .graphType(GraphType.SIMPLE)
                .order(order)
                .size(size)
                .weighted(true)
                .build()
                .write(FileType.ADJACENCY_LIST,
                        "src/data/graphs/large_graphs/simple/simple_order=" + order + "_size=" + size.name());
    }
}