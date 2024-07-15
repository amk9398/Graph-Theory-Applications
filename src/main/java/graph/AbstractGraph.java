package main.java.graph;

import main.java.connection.Connection;
import main.java.distance.Distance;
import main.java.walk.Tour;

import java.util.ArrayList;

public abstract class AbstractGraph {
    public abstract void addEdge(int v1, int v2);

    public abstract void addEdge(int v1, int v2, int weight);

    public abstract void addVertex();

    public abstract AbstractGraph clone();

    public abstract int degreeOf(int v);

    public int distance(int v1, int v2) {
        return Distance.dijkstra(this, v1, v2);
    }

    public abstract int[][] getAdjacencyMatrix();

    public abstract ArrayList<Edge> getEdgeList();

    public abstract int getEdgeWeight(int v1, int v2);

    public ArrayList<Integer> getMaxTour() {
        return Tour.fleury(this);
    }

    public abstract boolean hasEdge(int v1, int v2);

    public abstract boolean isComplete();

    public abstract boolean isEulerian();

    public abstract boolean isIdentical(AbstractGraph graph);

    public abstract boolean isSimple();

    public abstract int maxDegree();

    public abstract int minDegree();

    public SimpleGraph mst() {
        return Connection.kruskal(this);
    }

    public abstract ArrayList<Integer> neighborsOf(int v);

    public abstract int order();

    public abstract void removeEdge(int v1, int v2);

    public abstract void removeVertex(int v);

    public abstract int size();

    //    public abstract AbstractGraph complement();
    //    public abstract boolean isConnected();

    // isomorphisms, automorphisms
    // vertex/edge transitive
    // vertex/edge induced sub-graphs
    // minus, plus operations
    // degree of vertex
    // distance
    // subgraph
    // union
    // diameter
    // incidence matrix
    // underlying simple graph
    // girth
    // regular
}