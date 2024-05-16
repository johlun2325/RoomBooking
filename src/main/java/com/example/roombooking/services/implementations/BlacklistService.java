package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BlacklistCustomerDTO;
import com.example.roombooking.models.BlacklistStatus;
import com.example.roombooking.models.BlacklistedCustomer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


//    https://www.baeldung.com/java-httpclient-map-json-response

@Service
public class BlacklistService {

    private final ObjectMapper jsonMapper = new JsonMapper().registerModule(new JavaTimeModule());
    private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistService.class);


//    private HttpResponse<String> responseHandler(HttpRequest request) {
//        try {
//            return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private <T> T readJsonValue(HttpResponse<String> response, TypeReference<T> typeReference) {
//        try {
//            return jsonMapper.readValue(response.body(), typeReference);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public BlacklistStatus fetchBlacklistedStatusByEmail(String email) {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklistcheck/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            return jsonMapper.readValue(response.body(), BlacklistStatus.class);
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BlacklistedCustomer> fetchAllBlacklistedCustomers() {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            return Arrays.stream(jsonMapper.readValue(response.body(), BlacklistedCustomer[].class)).toList();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String addCustomerToBlacklist(BlacklistCustomerDTO blacklistCustomerDTO) {

        var allBlacklistedCustomers = fetchAllBlacklistedCustomers();
        boolean isBlacklisted = allBlacklistedCustomers.stream().anyMatch(customer -> customer.getEmail().equals(blacklistCustomerDTO.getEmail()));

        if (isBlacklisted) return "That customer is already blacklisted";

        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(blacklistCustomerDTO.getEmail(), blacklistCustomerDTO.getName(), blacklistCustomerDTO.isOk())))
                .build();
        try {
            response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get();
            return (response.statusCode() >= 200 && response.statusCode() < 300)
                    ? "Successfully added customer to blacklist"
                    : "Error while adding customer to blacklist";
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateCustomerToBlacklist(String email, String name, boolean isOk) {

        var allBlacklistedCustomers = fetchAllBlacklistedCustomers();
        boolean isBlacklisted = allBlacklistedCustomers.stream().anyMatch(customer -> customer.getEmail().equals(email));

        if (!isBlacklisted) return "That customer is not blacklisted";

        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(email, name, isOk)))
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return (response.statusCode() >= 200 && response.statusCode() < 300)
                    ? "Successfully updated customer in blacklist"
                    : "Error while updating customer in blacklist";
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
