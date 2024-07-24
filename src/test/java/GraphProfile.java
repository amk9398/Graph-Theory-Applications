package test.java;

import main.java.graph.Edge;
import main.java.utils.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphProfile {
    public boolean complete;
    public int[] degrees;
    public int[] dfsPath;
    public HashSet<Edge> directedCutEdges;
    public HashSet<Integer> directedCutVertices;
    public boolean directedCyclic;
    public boolean empty;
    public boolean eulerian;
    public String filename;
    public int maxDegree;
    public int minDegree;
    public int mstWeight;
    public int numStrongComponents;
    public int numWeakComponents;
    public int order;
    public int size;
    public HashSet<Edge> undirectedCutEdges;
    public HashSet<Integer> undirectedCutVertices;
    public boolean undirectedCyclic;
    public int[] unweightedDistance;
    public int[] weightedDistance;

    public static HashMap<String, GraphProfile> parseProfiles(String filename) {
        HashMap<String, GraphProfile> profiles = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                GraphProfile profile = new GraphProfile();
                String[] rowValues = line.split(", ");
                profile.filename = rowValues[0];
                profile.complete = Boolean.parseBoolean(rowValues[1]);
                profile.degrees = parseIntArray(rowValues[2]);
                profile.dfsPath = parseIntArray(rowValues[3]);
                profile.directedCutEdges = parseEdgeSet(rowValues[4]);
                profile.directedCutVertices = parseIntSet(rowValues[5]);
                profile.directedCyclic = Boolean.parseBoolean(rowValues[6]);
                profile.empty = Boolean.parseBoolean(rowValues[7]);
                profile.eulerian = Boolean.parseBoolean(rowValues[8]);
                profile.maxDegree = Integer.parseInt(rowValues[9]);
                profile.minDegree = Integer.parseInt(rowValues[10]);
                profile.mstWeight =Integer.parseInt(rowValues[11]);
                profile.numStrongComponents = Integer.parseInt(rowValues[12]);
                profile.numWeakComponents = Integer.parseInt(rowValues[13]);
                profile.order = Integer.parseInt(rowValues[14]);
                profile.size = Integer.parseInt(rowValues[15]);
                profile.undirectedCutEdges = parseEdgeSet(rowValues[16]);
                profile.undirectedCutVertices = parseIntSet(rowValues[17]);
                profile.undirectedCyclic = Boolean.parseBoolean(rowValues[18]);
                profile.unweightedDistance = parseIntArray(rowValues[19]);
                profile.weightedDistance = parseIntArray(rowValues[20]);
                profiles.put(profile.filename, profile);
            }
        } catch (IOException e) {
            Log.e("Could not read graph profiles");
        }

        return profiles;
    }

    private static int[] parseIntArray(String str) {
        String trimmed = str.substring(1, str.length() - 1);

        if (trimmed.isEmpty()) {
            return new int[0];
        }

        String[] stringNumbers = trimmed.split(",");
        int[] result = new int[stringNumbers.length];

        for (int i = 0; i < stringNumbers.length; i++) {
            result[i] = Integer.parseInt(stringNumbers[i]);
        }

        return result;
    }

    private static HashSet<Integer> parseIntSet(String str) {
        HashSet<Integer> set = new HashSet<>();
        String trimmed = str.substring(1, str.length() - 1);

        if (trimmed.isEmpty()) {
            return set;
        }

        String[] stringNumbers = trimmed.split(",");
        for (String stringNumber : stringNumbers) {
            set.add(Integer.parseInt(stringNumber));
        }

        return set;
    }

    private static HashSet<Edge> parseEdgeSet(String str) {
        HashSet<Edge> set = new HashSet<>();
        String trimmed = str.substring(1, str.length() - 1);

        if (trimmed.isEmpty()) {
            return set;
        }

        String[] edgesStrings = trimmed.split("\\),\\(");
        for (String edgeString : edgesStrings) {
            edgeString = edgeString.replace("(", "").replace(")", "");
            String[] vertices = edgeString.split(",");
            set.add(new Edge(Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1])));
        }

        return set;
    }
}
