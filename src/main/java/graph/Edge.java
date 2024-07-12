package main.java.graph;

public class Edge {
    public int v1;
    public int v2;
    public int weight;

    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Edge oEdge)) {
            return false;
        }

        return v1 == oEdge.v1 && v2 == oEdge.v2 && weight == oEdge.weight;
    }

    @Override
    public String toString() {
        return "(" + v1 + ", " + v2 + ")";
    }
}
