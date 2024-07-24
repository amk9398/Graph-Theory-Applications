package test.java.search;

import main.java.graph.simple.SimpleGraph;
import main.java.search.Search;
import org.junit.Assert;
import org.junit.Test;
import test.java.UnitTestClass;

import java.util.ArrayList;

public class SearchTest extends UnitTestClass {
    @Test
    public void testBfs() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            for (int i = 0; i < graph.order(); i++) {
                ArrayList<Integer> bfsPath = Search.bfs(graph, 0, i);
                int expected = getProfile(name).unweightedDistance[i];
                if (expected == -1) {
                    Assert.assertNull(bfsPath);
                } else {
                    Assert.assertNotNull(bfsPath);
                    Assert.assertEquals(getProfile(name).unweightedDistance[i], bfsPath.size() - 1);
                }
            }
        }, "SearchTest.testBfs");
    }

    @Test
    public void testDfs() {
        test(name -> {
            SimpleGraph graph = getSimpleGraph(name);
            ArrayList<Integer> dfsPath = Search.dfs(graph, 0);
            for (int i = 0; i < dfsPath.size(); i++) {
                Integer v = dfsPath.get(i);
                Assert.assertNotNull(v);
                Assert.assertEquals(getProfile(name).dfsPath[i], v.intValue());
            }
        }, "SearchTest.testDfs");
    }
}