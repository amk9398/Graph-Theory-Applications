package test.java.graph;

import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

public class AbstractGraphTest extends UnitTestClass {
    @Test
    public void testCenter() {
        test(name -> Assert.assertEquals(getProfile(name).center, getSimpleGraph(name).center()),
                "testCenter");
    }

    @Test
    public void testConnected() {
        test(name -> {
            int[] unweightedDistance = getProfile(name).unweightedDistance;

            for (int i = 1; i < unweightedDistance.length; i++) {
                boolean connected = unweightedDistance[i] != -1;
                Assert.assertEquals(connected, getSimpleGraph(name).connected(0, i));
            }
        }, "testConnected");
    }
}