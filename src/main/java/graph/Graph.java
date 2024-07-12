package main.java.graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private final int[][] adjacencyMatrix;
    private final HashMap<Integer, Integer> degreeMap = new HashMap<>();
    private final HashMap<Integer, ArrayList<Integer>> neighborMap = new HashMap<>();

    public Graph(int size) {
        adjacencyMatrix = new int[size][size];
        for (int v = 0; v < size; v++) {
            degreeMap.put(v, 0);
            neighborMap.put(v, new ArrayList<>());
        }
    }

    public Graph(Graph graph) {
        adjacencyMatrix = graph.copyAdjacencyMatrix();
        for (int v = 0; v < graph.size(); v++) {
            degreeMap.put(v, graph.degreeOf(v));
            neighborMap.put(v, graph.neighborsOf(v));
        }
    }

    public int[][] copyAdjacencyMatrix() {
        int[][] myInt = new int[adjacencyMatrix.length][];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int[] aMatrix = adjacencyMatrix[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }

        return myInt;
    }

    public int degreeOf(int v) {
        return degreeMap.get(v);
    }

    public ArrayList<Edge> getEdgeList() {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            for (int j = i; j < size(); j++) {
                int weight = adjacencyMatrix[i][j];
                if (weight > 0) {
                    edges.add(new Edge(i, j, weight));
                }
            }
        }

        return edges;
    }

    public ArrayList<Integer> neighborsOf(int v) {
        return neighborMap.get(v);
    }

    public void removeEdge(int start, int end) {
        setWeight(start, end, 0);
    }

    public void setEdge(int start, int end) {
        setWeight(start, end, 1);
    }

    public void setWeight(int start, int end, int weight) {
        int currWeight = adjacencyMatrix[start][end];
        if (currWeight > 0 && weight == 0) {
            decrementDegreeValue(start);
            decrementDegreeValue(end);
            removeEdgeFromNeighborMap(start, end);
        } else if (currWeight == 0 && weight > 0) {
            incrementDegreeValue(start);
            incrementDegreeValue(end);
            addEdgeToNeighborMap(start, end);
        }
        adjacencyMatrix[start][end] = weight;
        adjacencyMatrix[end][start] = weight;
    }

    public int size() {
        return adjacencyMatrix.length;
    }

    public int weight(Edge edge) {
        return weight(edge.v1, edge.v2);
    }

    public int weight(int start, int end) {
        return adjacencyMatrix[start][end];
    }

    private void addEdgeToNeighborMap(int v1, int v2) {
        neighborMap.get(v1).add(v2);
        neighborMap.get(v2).add(v1);
    }

    private void decrementDegreeValue(int v) {
        int degree = degreeMap.get(v);
        degreeMap.put(v, degree - 1);
    }

    private void incrementDegreeValue(int v) {
        int degree = degreeMap.get(v);
        degreeMap.put(v, degree + 1);
    }

    private void removeEdgeFromNeighborMap(int v1, int v2) {
        neighborMap.get(v1).remove((Integer) v2);
        neighborMap.get(v2).remove((Integer) v1);
    }
}