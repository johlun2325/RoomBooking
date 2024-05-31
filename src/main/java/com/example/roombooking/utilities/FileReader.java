package com.example.roombooking.utilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

//    public void updateFile(String content)  {
//        byte[] bytes = content.getBytes();
//        Path path = Paths.get(BOOKING_CONFIRMATION_FILE);
//        try {
//            Files.write(path, bytes);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public String extractPlainTextFromFile(String filePath) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            return doc.text();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error receiving plain text from a file";
        }
    }

    public void updateMessagePlainText(String filePath, String oldText, String newText) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            Elements elementsContainingText = doc.getElementsContainingOwnText(oldText);
            for (Element element : elementsContainingText) {
                String originalHtml = element.html();
                String modifiedHtml = originalHtml.replaceAll(oldText, newText);
                element.html(modifiedHtml);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
