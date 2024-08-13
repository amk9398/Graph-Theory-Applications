package test.java;

import main.java.graph.Graph;
import main.java.graph.GraphType;
import main.java.graph.simple.SimpleGraph;
import main.java.graph.simple.UndirectedGraph;
import main.java.utils.Log;
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

    private static final HashMap<String, Graph> graphs = new HashMap<>();
    private static HashMap<String, GraphProfile> profiles;

    @BeforeClass
    public static void initialize() {
        if (profiles != null) {
            return;
        }

        profiles = GraphProfile.parseProfiles("src/data/graph_profiles.txt");
        initGraph(BIPARTITE);
        initGraph(COMPLETE);
        initGraph(CONNECTED);
        initGraph(CYCLE);
        initGraph(DIRECTED_PATH);
        initGraph(DIRECTED_TREE);
        initGraph(DISCONNECTED);
        initGraph(EMPTY);
        initGraph(LARGE);
        initGraph(MEDIUM);
        initGraph(PATH);
        initGraph(SIMPLE);
        initGraph(SINGLE_EDGE);
        initGraph(SPARSE);
        initGraph(STAR);
        initGraph(TREE);
        initGraph(ZERO);
    }

    public UnitTestClass() {
        TEST_CLASS = getClass().getSimpleName();
    }

    protected static SimpleGraph getSimpleGraph(String name) {
        return (SimpleGraph) graphs.get(name);
    }

    protected static GraphProfile getProfile(String name) {
        return profiles.get(name);
    }

    protected static Set<String> getTestGraphs() {
        return graphs.keySet();
    }

    protected static UndirectedGraph getUndirectedGraph(String name) {
        return (UndirectedGraph) getSimpleGraph(name).as(GraphType.UNDIRECTED);
    }

    protected static void test(Assertion assertion, String method) {
        for (String name : graphs.keySet()) {
            String testName = TEST_CLASS + "." + method;
            Log.i("Testing " + getProfile(name).filename + " on " + testName);

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

    private static void initGraph(String name) {
        graphs.put(name, Graph.read(GRAPH_PATH + name + TXT).orElse(null));
    }
}