package com.example.roombooking.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        Path filePath = Paths.get(path);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFile(String path, List<String> lines) {
        Path filePath = Paths.get(path);

        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFile(String path, String target, String replacement) {
        List<String> lines = readFile(path);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            updatedLines.add(line.replace(target, replacement));
        }

        writeFile(path, updatedLines);
    }

}
