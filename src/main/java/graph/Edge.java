package main.java.graph;

import java.util.Objects;

public class Edge {
    public int v1;
    public int v2;
    public int weight;

    public Edge(int v1, int v2) {
        this(v1, v2, 1);
    }

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

        return v1 == oEdge.v1 && v2 == oEdge.v2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2);
    }

    @Override
    public String toString() {
        return "(" + v1 + ", " + v2 + ")";
    }

    public Edge getReverse() {
        return new Edge(v1, v1, weight);
    }
}
