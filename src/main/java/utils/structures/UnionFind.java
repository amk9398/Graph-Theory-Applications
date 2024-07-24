package main.java.utils.structures;

public class UnionFind {
    private final int[] vertexPointers;

    public UnionFind(int size) {
        vertexPointers = new int[size];
        for (int i = 0; i < size; i++) {
            vertexPointers[i] = i;
        }
    }

    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    public int find(int vertex) {
        if (vertex == vertexPointers[vertex]) {
            return vertex;
        }

        vertexPointers[vertex] = find(vertexPointers[vertex]);
        return vertexPointers[vertex];
    }

    public void union(int v1, int v2) {
        vertexPointers[find(v1)] = v2;
    }
}