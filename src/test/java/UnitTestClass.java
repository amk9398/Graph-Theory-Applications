package test.java;

import main.java.graph.simple.SimpleGraph;
import main.java.graph.simple.UndirectedGraph;
import main.java.utils.Log;
import main.java.utils.io.GraphReader;
import org.junit.BeforeClass;

import java.util.HashMap;
import java.util.Set;

public class UnitTestClass {
    public static final String BIPARTITE = "bipartite_graph";
    public static final String COMPLETE = "complete_graph";
    public static final String CONNECTED = "connected_graph";
    public static final String CYCLE = "cycle_graph";
    public static final String DIRECTED_PATH = "directed_path";
    public static final String DIRECTED_TREE = "directed_tree";
    public static final String DISCONNECTED = "disconnected_graph";
    public static final String EMPTY = "empty_graph";
    public static final String LARGE = "large_graph";
    public static final String MEDIUM = "medium_graph";
    public static final String PATH = "path_graph";
    public static final String SIMPLE = "simple_graph";
    public static final String SINGLE_EDGE = "single_edge_graph";
    public static final String SPARSE = "sparse_graph";
    public static final String STAR = "star_graph";
    public static final String TREE = "tree_graph";
    public static final String ZERO = "zero_graph";
    private static final String GRAPH_PATH = "src/data/graphs/";
    private static final String TXT = ".txt";

    protected static String TEST_CLASS;

    private static final HashMap<String, SimpleGraph> graphs = new HashMap<>();
    private static HashMap<String, GraphProfile> profiles;

    @BeforeClass
    public static void initialize() {
        if (profiles != null) {
            return;
        }

        profiles = GraphProfile.parseProfiles("src/data/graph_profiles.txt");
        graphs.put(BIPARTITE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + BIPARTITE + TXT));
        graphs.put(COMPLETE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + COMPLETE + TXT));
        graphs.put(CONNECTED, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + CONNECTED + TXT));
        graphs.put(CYCLE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + CYCLE + TXT));
        graphs.put(DIRECTED_PATH, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + DIRECTED_PATH + TXT));
        graphs.put(DIRECTED_TREE,GraphReader.readSimpleGraphFromFile(GRAPH_PATH + DIRECTED_TREE + TXT));
        graphs.put(DISCONNECTED, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + DISCONNECTED + TXT));
        graphs.put(EMPTY, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + EMPTY + TXT));
        graphs.put(LARGE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + LARGE + TXT));
        graphs.put(MEDIUM, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + MEDIUM + TXT));
        graphs.put(PATH, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + PATH + TXT));
        graphs.put(SIMPLE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + SIMPLE + TXT));
        graphs.put(SINGLE_EDGE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + SINGLE_EDGE + TXT));
        graphs.put(SPARSE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + SPARSE + TXT));
        graphs.put(STAR, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + STAR + TXT));
        graphs.put(TREE, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + TREE + TXT));
        graphs.put(ZERO, GraphReader.readSimpleGraphFromFile(GRAPH_PATH + ZERO + TXT));
    }

    public UnitTestClass() {
        TEST_CLASS = getClass().getSimpleName();
    }

    protected static SimpleGraph getSimpleGraph(String name) {
        return graphs.get(name);
    }

    protected static GraphProfile getProfile(String name) {
        return profiles.get(name);
    }

    protected static Set<String> getTestGraphs() {
        return graphs.keySet();
    }

    protected static UndirectedGraph getUndirectedGraph(String name) {
        return (UndirectedGraph) getSimpleGraph(name).as(SimpleGraph.UNDIRECTED_GRAPH);
    }

    protected static void test(Assertion assertion, String method) {
        for (String name : graphs.keySet()) {
            String testName = TEST_CLASS + "." + method;
            Log.d("Testing " + getProfile(name).filename + " on " + testName);

            try {
                assertion.check(name);
            } catch (AssertionError e) {
                Log.w("Failed " + testName + " on " + getProfile(name).filename);
                throw e;
            } catch (Exception e) {
                Log.e("Failed " + testName + " on " + getProfile(name).filename);
                throw e;
            }
        }
    }

    protected interface Assertion {
        void check(String name);
    }
}