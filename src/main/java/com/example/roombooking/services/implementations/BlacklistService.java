package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.dto.BlacklistedCustomerDTO;
import com.example.roombooking.models.External.BlacklistStatus;
import com.example.roombooking.models.External.BlacklistedCustomer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final IntegrationProperties integrationProperties;

    @Autowired
    public BlacklistService(IntegrationProperties integrationProperties) {
        this.integrationProperties = integrationProperties;
    }

    private boolean isBlacklisted(String email) {
        var allBlacklistedCustomers = fetchAllBlacklistedCustomers();
        boolean isBlacklisted = allBlacklistedCustomers
                .stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
        LOGGER.info("Checking blacklist status for email {}: {}", email, (isBlacklisted ? "BlacklistedCustomer" : "Not blacklisted"));
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
        String url = integrationProperties.getBlacklist().getCheck();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/%s".formatted(email)))
                .header(integrationProperties.getBlacklist().getHttpName(), integrationProperties.getBlacklist().getHttpValue())
                .GET()
                .build();

        LOGGER.info("Fetching blacklist status for email: {}", email);
        return readJsonValue(responseHandler(request), BlacklistStatus.class);
    }

    public List<BlacklistedCustomer> fetchAllBlacklistedCustomers() {
        String url = integrationProperties.getBlacklist().getUrl();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(integrationProperties.getBlacklist().getHttpName(), integrationProperties.getBlacklist().getHttpValue())
                .GET()
                .build();

        LOGGER.info("Fetching all blacklisted customers");
        return Arrays.stream(readJsonValue(responseHandler(request), BlacklistedCustomer[].class)).toList();
    }

    public String addCustomerToBlacklist(BlacklistedCustomerDTO blacklistedCustomerDTO) {
        String url = integrationProperties.getBlacklist().getUrl();
        String message;
        if (isBlacklisted(blacklistedCustomerDTO.getEmail())) {
            message = "Customer with email %s, already blacklisted".formatted(blacklistedCustomerDTO.getEmail());
            return message;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(integrationProperties.getBlacklist().getHttpName(), integrationProperties.getBlacklist().getHttpValue())
                .POST(HttpRequest.BodyPublishers.ofString(integrationProperties.getBlacklist().getRequestBody()
                        .formatted(blacklistedCustomerDTO.getEmail(), blacklistedCustomerDTO.getName(), blacklistedCustomerDTO.isOk())))
                .build();

        message = "Customer with email %s, successfully added in Blacklist".formatted(blacklistedCustomerDTO.getEmail());
        return message(responseHandler(request).statusCode(), message);
    }

    public String updateCustomerToBlacklist(BlacklistedCustomerDTO blacklistedCustomerDTO) {
        String url = integrationProperties.getBlacklist().getUrl();
        String message;
        if (!isBlacklisted(blacklistedCustomerDTO.getEmail())) {
            message = "Customer with email %s is not blacklisted".formatted(blacklistedCustomerDTO.getEmail());
            return message;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/%s".formatted(blacklistedCustomerDTO.getEmail())))
                .header(integrationProperties.getBlacklist().getHttpName(), integrationProperties.getBlacklist().getHttpValue())
                .PUT(HttpRequest.BodyPublishers.ofString(integrationProperties.getBlacklist().getRequestBody()
                        .formatted(blacklistedCustomerDTO.getEmail(), blacklistedCustomerDTO.getName(), blacklistedCustomerDTO.isOk())))
                .build();

        message = "Customer with email %s, successfully updated in Blacklist".formatted(blacklistedCustomerDTO.getEmail());
        return message(responseHandler(request).statusCode(), message);
    }
}
