package main.java.utils.structures;

import java.util.HashMap;

public class EdgeList extends HashMap<Edge, Integer> {
    public EdgeList() {
        super();
    }

    public EdgeList(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Integer get(Object object) {
        if (!(object instanceof Edge edge)) {
            return null;
        }

        Integer i = super.get(edge);
        if (i == null) {
            return 0;
        }

        return i;
    }

    public int get(int v1, int v2) {
        Integer i = get(new Edge(v1, v2));
        if (i == null) {
            return 0;
        }

        return i;
    }

    public void put(int v1, int v2, int weight) {
        remove(v1, v2);
        put(new Edge(v1, v2, weight), weight);
    }

    public void remove(int v1, int v2) {
        remove(new Edge(v1, v2));
    }
}