package com.example.roombooking.utilities;

import com.example.roombooking.models.BlacklistStatus;
import com.example.roombooking.models.BlacklistedCustomer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

public class BlacklistJsonMapper {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    public Optional<BlacklistStatus> fetchBlacklistedStatusByEmail(String email) {

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklistcheck/%s".formatted(email))) // group jeri
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            BlacklistStatus status = objectMapper.readValue(response.body(), BlacklistStatus.class);

            return (status == null) ? Optional.empty() : Optional.of(status);
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
