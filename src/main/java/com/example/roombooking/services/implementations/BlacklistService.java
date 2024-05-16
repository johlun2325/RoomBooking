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

    private boolean isBlacklisted(String email) {
        var allBlacklistedCustomers = fetchAllBlacklistedCustomers();
        return allBlacklistedCustomers
                .stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    private HttpResponse<String> responseHandler(HttpRequest request) {
        try {
            return HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T readJsonValue(HttpResponse<String> response, Class<T> clazz) {
        try {
            return jsonMapper.readValue(response.body(), clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public BlacklistStatus fetchBlacklistedStatusByEmail(String email) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklistcheck/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return readJsonValue(responseHandler(request), BlacklistStatus.class);
    }

    public List<BlacklistedCustomer> fetchAllBlacklistedCustomers() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        return Arrays.stream(readJsonValue(responseHandler(request), BlacklistedCustomer[].class)).toList();
    }

    public String addCustomerToBlacklist(BlacklistCustomerDTO blacklistCustomerDTO) {
        if (isBlacklisted(blacklistCustomerDTO.getEmail()))
            return "That customer is already blacklisted";
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(blacklistCustomerDTO.getEmail(), blacklistCustomerDTO.getName(), blacklistCustomerDTO.isOk())))
                .build();

        int status = responseHandler(request).statusCode();
        return (status >= 200 && status < 300)
                ? "Successfully added customer to blacklist"
                : "Error while adding customer to blacklist";
    }

    public String updateCustomerToBlacklist(String email, String name, boolean isOk) {
        if (!isBlacklisted(email))
            return "That customer is not blacklisted";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/jeri/blacklist/%s".formatted(email)))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{\"email\":\"%s\", \"name\":\"%s\", \"isOk\":\"%s\"}"
                        .formatted(email, name, isOk)))
                .build();

        int status = responseHandler(request).statusCode();
        return (status >= 200 && status < 300)
                ? "Successfully updated customer in blacklist"
                : "Error while updating customer in blacklist";
    }

}
