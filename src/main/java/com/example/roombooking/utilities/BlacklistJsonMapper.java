package com.example.roombooking.utilities;

import com.example.roombooking.models.BlacklistStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BlacklistJsonMapper {

//    https://www.baeldung.com/java-httpclient-map-json-response

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public BlacklistStatus fetchBlacklistedStatusByEmail(String email) {

        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklistcheck/%s".formatted(email))) // group jeri
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), BlacklistStatus.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    private String fetchAllBlacklistedCustomers() throws Exception {
//        HttpResponse<String> response;
//        try (HttpClient client = HttpClient.newHttpClient()) {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist")) // gruppnamn: jeri
//                    .header("Content-Type", "application/json")
//                    .GET()
//                    .build();
//
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        }
//        return response.body();
//    }

//    public List<BlacklistedCustomer> getAllBlacklistedCustomers(String content) {
//        try {
//            return objectMapper.readValue(content, new TypeReference<>() {});
//        } catch (IOException ioe) {
//            throw new CompletionException(ioe);
//        }
//    }

//    private BlacklistedCustomer asyncJackson() throws Exception {
//
//        List<BlacklistedCustomer> blacklistedCustomers;
//        try (HttpClient client = HttpClient.newHttpClient()) {
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
//                    .build();
//
//            BlacklistJsonMapper todoAppClient = new BlacklistJsonMapper();
//
//            blacklistedCustomers = client
//                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
//                    .thenApply(HttpResponse::body)
//                    .thenApply(todoAppClient::getAllBlacklistedCustomers)
//                    .get();
//        }
//
//        return blacklistedCustomers.get(0);
//    }
}
