package test.java.walk;

import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

import java.util.ArrayList;
import java.util.Arrays;

public class TourTest extends UnitTestClass {
    @Test
    public void testFleury() {
        Assert.assertEquals(
                new ArrayList<>(Arrays.asList(0, 2, 0, 3, 1, 2, 1, 3, 0)),
                getSimpleGraph(BIPARTITE).getEulerTour()
        );
        Assert.assertEquals(
                new ArrayList<>(Arrays.asList(0, 1, 0, 2, 0, 3, 0, 4, 1, 2, 1, 3, 1, 4, 2, 3, 2, 4, 3, 4, 0)),
                getSimpleGraph(CONNECTED).getEulerTour()
        );
        Assert.assertEquals(
                new ArrayList<>(Arrays.asList(0, 1, 0, 3, 2, 1, 2, 3, 0)),
                getSimpleGraph(CYCLE).getEulerTour()
        );
        Assert.assertEquals(
                new ArrayList<>(),
                getSimpleGraph(ZERO).getEulerTour()
        );
    }
}