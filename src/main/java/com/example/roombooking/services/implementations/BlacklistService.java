package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BlacklistCustomerDTO;
import com.example.roombooking.models.BlacklistStatus;
import com.example.roombooking.models.BlacklistedCustomer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BlacklistService {

    private final ObjectMapper jsonMapper = new JsonMapper().registerModule(new JavaTimeModule());
    private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistService.class);

    private boolean isBlacklisted(String email) {
        var allBlacklistedCustomers = fetchAllBlacklistedCustomers();
        boolean isBlacklisted = allBlacklistedCustomers
                .stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
        LOGGER.info("Checking blacklist status for email {}: {}", email, (isBlacklisted ? "Blacklisted" : "Not blacklisted"));
        return isBlacklisted;
    }

    private HttpResponse<String> responseHandler(HttpRequest request) {
        try {
            LOGGER.info("Sending HTTP request to: {}", request.uri());
            return HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T readJsonValue(HttpResponse<String> response, Class<T> clazz) {
        try {
            LOGGER.info("Reading JSON response");
            return jsonMapper.readValue(response.body(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String message(int status, String successMessage) {
        String message = switch (status / 100) {
            case 2 -> successMessage;
            case 4 -> "Client error. Please check your request.";
            case 5 -> "Server error. Please try again later.";
            default -> "Unexpected status: " + status;
        };
        LOGGER.info("Received response status: {}", status);
        LOGGER.info("Response message: {}", message);
        return message;
    }

    public BlacklistStatus fetchBlacklistedStatusByEmail(String email) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklistcheck/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        LOGGER.info("Fetching blacklist status for email: {}", email);
        return readJsonValue(responseHandler(request), BlacklistStatus.class);
    }

    public List<BlacklistedCustomer> fetchAllBlacklistedCustomers() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        LOGGER.info("Fetching all blacklisted customers");
        return Arrays.stream(readJsonValue(responseHandler(request), BlacklistedCustomer[].class)).toList();
    }

    public String addCustomerToBlacklist(BlacklistCustomerDTO blacklistCustomerDTO) {
        String message;
        if (isBlacklisted(blacklistCustomerDTO.getEmail())) {
            message = "Customer with email %s, already blacklisted".formatted(blacklistCustomerDTO.getEmail());
            return message;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(blacklistCustomerDTO.getEmail(), blacklistCustomerDTO.getName(), blacklistCustomerDTO.isOk())))
                .build();

        message = "Customer with email %s, successfully added in Blacklist".formatted(blacklistCustomerDTO.getEmail());
        return message(responseHandler(request).statusCode(), message);
    }

    public String updateCustomerToBlacklist(String email, String name, boolean isOk) {
        String message;
        if (!isBlacklisted(email)) {
            message = "Customer with email %s is not blacklisted".formatted(email);
            return message;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}".formatted(email, name, isOk)))
                .build();

        message = "Customer with email %s, successfully updated in Blacklist".formatted(email);
        return message(responseHandler(request).statusCode(), message);
    }
}
