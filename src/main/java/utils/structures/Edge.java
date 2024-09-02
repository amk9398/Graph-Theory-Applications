package main.java.utils.structures;

import main.java.utils.Utils;

import java.util.Objects;
import java.util.Optional;

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

    public Edge clone() {
        return new Edge(v1, v2, weight);
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

    /**
     * Return a new edge from a String representation. E.g., "0, 1" represents
     * an edge from vertex 0 to vertex 1. "3, 4, 2" represents an edge from
     * vertex 3 to vertex 4 with a weight of 2.
     */
    public static Optional<Edge> parse(String str) {
        if (str == null || str.isEmpty()) {
            return Optional.empty();
        }

        String[] split = str.split(",");
        if (split.length != 2 && split.length != 3) {
            return Optional.empty();
        }

        Optional<Integer> v1 = Utils.tryParseInt(split[0].trim());
        Optional<Integer> v2 = Utils.tryParseInt(split[1].trim());

        if (v1.isEmpty() || v2.isEmpty() || v1.get() < 0 || v2.get() < 0) {
            return Optional.empty();
        }

        Optional<Integer> weight = Optional.of(1);
        if (split.length == 3) {
           weight = Utils.tryParseInt(split[2].trim());
           if (weight.isEmpty()) {
               return Optional.empty();
           }
        }

        return Optional.of(new Edge(v1.get(), v2.get(), weight.get()));
    }
}