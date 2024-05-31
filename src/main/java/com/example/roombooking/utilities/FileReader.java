package com.example.roombooking.utilities;

import java.io.BufferedReader;
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

    public void updateFile(String filePath, String content)  {
        byte[] bytes = content.getBytes();
        Path path = Paths.get(filePath);
        try {
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String fileContentToString(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        lines.forEach(line -> builder.append(line).append("\n"));

        return builder.toString();
    }

}
