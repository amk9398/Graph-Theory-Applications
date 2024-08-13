package main.java.utils.io;

import main.java.graph.Graph;
import main.java.utils.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GraphWriter {
    public static void write(Graph graph, FileType fileType, String path) {
        String header = "!" + fileType.toString() + " !" + graph.getType().toString() + "\n";
        String stringRepresentation = fileType.toString(graph);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(header + stringRepresentation);
        } catch (IOException e) {
            Log.e("Could not write graph path '" + path + "'");
        }
    }
}