package com.example.roombooking.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public String readFile(String path) {
        StringBuilder content = new StringBuilder();
        Path filePath = Paths.get(path);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
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

//    public String extractPlainTextFromFile(String filePath) {
//        try {
//            File input = new File(filePath);
//            Document doc = Jsoup.parse(input, "UTF-8");
//
//            return doc.text();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error receiving plain text from a file";
//        }
//    }
//

}
