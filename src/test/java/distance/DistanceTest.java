package test.java.distance;

import main.java.distance.Distance;
import main.java.graph.simple.SimpleGraph;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

import java.util.Arrays;

public class DistanceTest extends UnitTestClass {
    @Test
    public void testDijkstra() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (int i = 0; i < graph.order(); i++) {
                int expectedDistance = getProfile(name).weightedDistance[i];
                if (expectedDistance == -1) {
                    Assert.assertNull(Distance.dijkstra(graph, 0, i));
                } else {
                    Assert.assertEquals(expectedDistance, Distance.dijkstra(graph, 0, i).intValue());
                }
            }
        }, "DistanceTest.testDijkstra");
    }
}