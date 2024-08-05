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

    /**
     * Returns a new edge that is the reverse of the current edge.
     * The reverse edge will have the same weight, but its vertices will be swapped.
     */
    public Edge getReverse() {
        return new Edge(v2, v1, weight);
    }

    /**
     * Checks if this edge is a link (i.e., it connects two different vertices).
     */
    public boolean isLink() {
        return v1 != v2;
    }

    /**
     * Checks if this edge is a loop (i.e., it connects a vertex to itself).
     */
    public boolean isLoop() {
        return v1 == v2;
    }

}
