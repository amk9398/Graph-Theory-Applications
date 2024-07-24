package main.java.graph.simple;

import main.java.graph.Edge;

import java.util.HashSet;

public class UndirectedGraph extends SimpleGraph {
    public UndirectedGraph(int order) {
        super(order);
    }

    private UndirectedGraph(UndirectedGraph undirectedGraph) {
        super(undirectedGraph);
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (!isValidEdge(v1, v2) || weight == getEdgeWeight(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = weight;
        adjacencyMatrix[v2][v1] = weight;
        degreeMap.put(v1, degreeOf(v1) + 1);
        degreeMap.put(v2, degreeOf(v2) + 1);
        edges.add(new Edge(v1, v2, weight));
        edges.add(new Edge(v2, v1, weight));
        neighborMap.get(v1).add(v2);
        neighborMap.get(v2).add(v1);
    }

    @Override
    public UndirectedGraph clone() {
        return new UndirectedGraph(this);
    }

    @Override
    public boolean isComplete() {
        return size() == order() * (order() - 1) / 2;
    }

    @Override
    public boolean isConnected() {
        return dfs().size() == order();
    }

    @Override
    public boolean isCyclic() {
        HashSet<Integer> visited = new HashSet<>();

        for (int v = 0; v < order(); v++) {
            if (!visited.contains(v)) {
                if (isCyclicH(v, -1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int numComponents() {
        int numComponents = 0;
        HashSet<Integer> visited = new HashSet<>();

        for (int v = 0; v < order(); v++) {
            if (!visited.contains(v)) {
                visited.addAll(dfs(v));
                numComponents++;
            }
        }

        return numComponents;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!isValidEdge(v1, v2) || !hasEdge(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = 0;
        adjacencyMatrix[v2][v1] = 0;
        degreeMap.put(v1, degreeOf(v1) - 1);
        degreeMap.put(v2, degreeOf(v2) - 1);
        edges.remove(new Edge(v1, v2));
        edges.remove(new Edge(v2, v1));
        neighborMap.get(v1).remove(v2);
        neighborMap.get(v2).remove(v1);
    }

    @Override
    public int size() {
        return super.size() / 2;
    }

    private boolean isCyclicH(int v, int parent, HashSet<Integer> visited) {
        visited.add(v);

        for (Integer i : neighborsOf(v)) {
            if (!visited.contains(i)) {
                if (isCyclicH(i, v, visited)) {
                    return true;
                }
            } else if (i != parent) {
                return true;
            }
        }

        return false;
    }
}