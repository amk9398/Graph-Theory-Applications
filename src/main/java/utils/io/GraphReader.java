package main.java.utils.io;

import main.java.graph.Graph;
import main.java.graph.GraphType;
import main.java.utils.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GraphReader {
    public static Optional<Graph> read(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            if (!isValidHeader(line)) {
                Log.w("Graph file header is invalid");
                return Optional.empty();
            }

            Optional<FileType> optionalFileType = getFileTypeFromHeader(line);
            if (optionalFileType.isEmpty()) {
                Log.w("Unrecognized graph file type");
                return Optional.empty();
            }

            FileType fileType = optionalFileType.get();
            GraphType graphType = getGraphTypeFromHeader(line);

            ArrayList<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            return fileType.read(lines.toArray(new String[0]), graphType);
        } catch (IOException e) {
            Log.e("Invalid file location: '" + filename + "'");
        }

        return Optional.empty();
    }

    private static boolean areValidHeaderFields(String[] headerFields) {
        if (headerFields == null || headerFields.length == 0) {
            return false;
        }

        for (String field : headerFields) {
            if (!field.startsWith("!")) {
                return false;
            }
        }

        return true;
    }

    private static Optional<FileType> getFileTypeFromHeader(String header) {
        return FileType.fromString(header.split(" ")[0].replace("!", ""));
    }

    private static GraphType getGraphTypeFromHeader(String header) {
        String[] headerString = header.split(" ");

        if (headerString.length < 2) {
            return GraphType.DEFAULT_GRAPH_TYPE;
        }

        return GraphType.fromString(headerString[1].replace("!", ""));
    }

    private static boolean isValidHeader(String header) {
        if (header == null || header.isEmpty()) {
            return false;
        }

        String[] headerFields = header.split(" ");
        return areValidHeaderFields(headerFields);
    }
}