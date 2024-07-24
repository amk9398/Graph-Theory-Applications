package test.java.distance;

import main.java.distance.Distance;
import main.java.graph.simple.SimpleGraph;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

public class DistanceTest extends UnitTestClass {
    @Test
    public void testDijkstra() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (int i = 0; i < graph.order(); i++) {
                Assert.assertEquals(getProfile(name).weightedDistance[i], Distance.dijkstra(graph, 0, i));
            }
        }, "DistanceTest.testDijkstra");
    }
}