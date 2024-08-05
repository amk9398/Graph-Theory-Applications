package test.java.connection;

import main.java.graph.Edge;
import main.java.graph.simple.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

public class ConnectionTest extends UnitTestClass {
    @Test
    public void testKirchhoff() {
        test(name -> Assert.assertEquals(getProfile(name).numSpanningTrees, getUndirectedGraph(name).numSpanningTrees()),
                "ConnectionTest.testKirchhoff");
    }

    @Test
    public void testKosaraju() {
        test(name -> Assert.assertEquals(getProfile(name).numStrongComponents, getSimpleGraph(name).numComponents()),
                "ConnectionTest.testKosaraju");
    }

    @Test
    public void testKruskal() {
        test(name -> {
            int expected = getProfile(name).mstWeight;
            UndirectedGraph mst = getUndirectedGraph(name).mst();
            if (expected == -1) {
                Assert.assertNull(mst);
            } else {
                Assert.assertNotNull(mst);
                int mstWeight = 0;
                for (Edge edge : mst.getEdges()) {
                    mstWeight += edge.weight;
                }
                Assert.assertEquals(expected, mstWeight);
            }
        }, "ConnectionTest.testKruskal");
    }
}