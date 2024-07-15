package main.java.graph;

import main.java.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SimpleGraph extends AbstractGraph {
    protected int[][] adjacencyMatrix;
    protected final HashMap<Integer, Integer> degreeMap = new HashMap<>();
    protected final HashMap<Integer, ArrayList<Integer>> neighborMap = new HashMap<>();
    protected int size = 0;

    public SimpleGraph(int order) {
        adjacencyMatrix = new int[order][order];
        for (int v = 0; v < order; v++) {
            degreeMap.put(v, 0);
            neighborMap.put(v, new ArrayList<>());
        }
    }

    private SimpleGraph(SimpleGraph simpleGraph) {
        adjacencyMatrix = Utils.deepCopy2dIntArray(simpleGraph.getAdjacencyMatrix());
        for (int v = 0; v < simpleGraph.order(); v++) {
            degreeMap.put(v, simpleGraph.degreeOf(v));
            neighborMap.put(v, simpleGraph.neighborsOf(v));
        }
        size = simpleGraph.size();
    }

    @Override
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 1);
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (hasEdge(v1, v2) || weight == 0) {
            return;
        }

        adjacencyMatrix[v1][v2] = weight;
        degreeMap.put(v1, degreeOf(v1) + 1);
        neighborMap.get(v1).add(v2);
        size++;
    }

    @Override
    public void addVertex() {
        int[][] matrix = new int[size + 1][];
        for (int i = 0; i < size; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, matrix[i], 0, size);
        }
        adjacencyMatrix = matrix;
        degreeMap.put(size + 1, 0);
        neighborMap.put(size + 1, new ArrayList<>());
        size++;
    }

    @Override
    public SimpleGraph clone() {
        return new SimpleGraph(this);
    }

    @Override
    public int degreeOf(int v) {
        return degreeMap.get(v);
    }

    @Override
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public ArrayList<Edge> getEdgeList() {
        ArrayList<Edge> edgeList = new ArrayList<>();

        for (int i = 0; i < order(); i++) {
            for (int j = i; j < order(); j++) {
                int weight = getEdgeWeight(i, j);
                if (weight > 0) {
                    edgeList.add(new Edge(i, j, weight));
                }
            }
        }

        return edgeList;
    }

    @Override
    public int getEdgeWeight(int v1, int v2) {
        return adjacencyMatrix[v1][v2];
    }

    @Override
    public boolean hasEdge(int v1, int v2) {
        return getEdgeWeight(v1, v2) != 0;
    }

    @Override
    public boolean isComplete() {
        return size() == order() * (order() - 1);
    }

    @Override
    public boolean isEulerian() {
        for (int i = 0; i < order(); i++) {
            if (degreeOf(i) % 2 == 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isIdentical(AbstractGraph graph) {
        if (!graph.isSimple()
                || order() != graph.order()
                || size() != graph.size()) {
            return false;
        }

        return Arrays.deepEquals(adjacencyMatrix, graph.getAdjacencyMatrix());
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public int maxDegree() {
        int maxDegree = Integer.MIN_VALUE;
        for (int v = 0; v < order(); v++) {
            if (degreeOf(v) > maxDegree) {
                maxDegree = degreeOf(v);
            }
        }

        return maxDegree;
    }

    @Override
    public int minDegree() {
        int minDegree = Integer.MAX_VALUE;
        for (int v = 0; v < order(); v++) {
            if (degreeOf(v) < minDegree) {
                minDegree = degreeOf(v);
            }
        }

        return minDegree;
    }

    @Override
    public ArrayList<Integer> neighborsOf(int v) {
        return neighborMap.get(v);
    }

    @Override
    public int order() {
        return adjacencyMatrix.length;
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (!hasEdge(v1, v2)) {
            return;
        }

        adjacencyMatrix[v1][v2] = 0;
        degreeMap.put(v1, degreeOf(v1) - 1);
        neighborMap.get(v1).remove((Integer) v2);
        size--;
    }

    @Override
    public void removeVertex(int v) {
        int[][] matrix = new int[size - 1][];

        int skipI = 0;
        for (int i = 0; i < size; i++) {
            if (i == v) {
                skipI = 1;
                continue;
            }
            int skipJ = 0;
            for (int j = 0; j < size; j++) {
                if (j == v) {
                    skipJ = 1;
                    continue;
                }
                matrix[i - skipI][j - skipJ] = adjacencyMatrix[i][j];
            }
        }

        adjacencyMatrix = matrix;
        degreeMap.remove(v, 0);
        neighborMap.remove(v);
        size--;
    }

    @Override
    public int size() {
        return size;
    }
}