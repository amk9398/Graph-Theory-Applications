package main.java.utils.structures;

import java.util.HashMap;

public class UnionFind {
    private final HashMap<Integer, Integer> vertexPointer = new HashMap<>();

    public UnionFind(int size) {
        for (int i = 0; i < size; i++) {
            vertexPointer.put(i, null);
        }
    }

    public int find(int vertex) {
        Integer dst = vertexPointer.get(vertex);
        if (dst == null) {
            return vertex;
        }

        return find(dst);
    }

    public void union(int comp1, int comp2) {
        int target = find(comp1);
        vertexPointer.put(target, comp2);
    }
}