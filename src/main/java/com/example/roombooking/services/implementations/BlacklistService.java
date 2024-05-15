package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BlacklistCustomerDTO;
import com.example.roombooking.models.BlacklistStatus;
import com.example.roombooking.models.BlacklistedCustomer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


//    https://www.baeldung.com/java-httpclient-map-json-response

@Service
public class BlacklistService {

    private final ObjectMapper jsonMapper = new JsonMapper().registerModule(new JavaTimeModule());

    public BlacklistCustomerDTO convertToBlacklistDto(BlacklistedCustomer customer) {
        return BlacklistCustomerDTO.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .isOk(customer.isOk())
                .build();
    }

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
            return jsonMapper.readValue(response.body(), BlacklistStatus.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Vad ska jag returera
    public List<BlacklistedCustomer> addCustomerToBlacklist(BlacklistCustomerDTO blacklistCustomerDTO) {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklist")) // group jeri
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(blacklistCustomerDTO.getEmail(), blacklistCustomerDTO.getName(), blacklistCustomerDTO.isOk())))
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.stream(jsonMapper.readValue(response.body(), BlacklistedCustomer[].class)).toList();
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
//            BlacklistService todoAppClient = new BlacklistService();
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
