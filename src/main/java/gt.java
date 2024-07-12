package main.java;

import main.java.connection.Connection;
import main.java.distance.Distance;
import main.java.graph.Graph;
import main.java.utils.io.GraphReader;
import main.java.walk.Tour;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class gt {
    public static void main(String[] args) {
        int optionsIndex = 2;
        String output;
        String outputFile = "";

        if (args.length < 2) {
            printUsageMessage();
            return;
        }

        String graphFile = args[0];
        String command = args[1];

        Graph graph = GraphReader.readGraphFromFile(graphFile);
        if (graph == null) {
            System.err.println("Could not read graph file.");
            return;
        }

        switch (command) {
            case "dist":
                Integer start = null;
                Integer end = null;

                if (args.length < 3) {
                    printUsageMessage();
                    return;
                }

                try {
                    start = Integer.parseInt(args[2]);
                    if (args.length >= 4) {
                        end = Integer.parseInt(args[3]);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Start and end vertices should be integers.");
                    return;
                }

                if (end == null) {
                    optionsIndex = 3;
                    output = Arrays.toString(Distance.dijkstra(graph, start));
                } else {
                    optionsIndex = 4;
                    output = String.valueOf(Distance.dijkstra(graph, start, end));
                }
                break;
            case "eulerian":
                ArrayList<Integer> tour = Tour.fleury(graph);
                output = String.valueOf(Tour.isEulerTour(graph, tour));
                break;
            case "mst":
                output = String.valueOf(Connection.kruskal(graph).getEdgeList());
                break;
            case "tour":
                output = String.valueOf(Tour.fleury(graph));
                break;
            case "help":
            default:
                printUsageMessage();
                return;
        }

        for (int i = optionsIndex; i < args.length; i++) {
            String flag = args[i];

            switch (flag) {
                case "-o":
                case "--output":
                    if (args.length <= i + 1) {
                        printUsageMessage();
                        return;
                    }

                    outputFile = args[++i];
                    break;
                default:
                    printUsageMessage();
                    return;
            }
        }

        if (outputFile.isEmpty()) {
            System.out.println(output);
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
                bw.write(output);
            } catch (IOException e) {
                System.err.println("Could not write to '" + output + "'.");
            }
        }
    }

    private static void printUsageMessage() {
        System.out.println("Usage: java gt <graph-file> <command | [args]> [options]");
        System.out.println("Commands:");
        System.out.println("  dist <start | [end]> - Calculate distance from start vertex. If end is not set, will output distance array to each vertex.");
        System.out.println("  eulerian - Determines if given graph is eulerian.");
        System.out.println("  mst  - Outputs list of edges in Minimum Spanning Tree (MST).");
        System.out.println("  tour - Outputs a max tour.");
        System.out.println("Options:");
        System.out.println("  -o <output-file> - specify an output file");
    }
}