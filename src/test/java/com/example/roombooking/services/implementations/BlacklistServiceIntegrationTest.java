package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BlacklistServiceIntegrationTest {

    @Autowired
    StreamProvider streamProvider;

    @Autowired
    IntegrationProperties integrationProperties;

    @Test
    void willFetchBlacklistedCustomersTest() throws IOException {
        Scanner s = new Scanner(streamProvider.getDataStream(integrationProperties.getBlacklist().getUrl())).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"id\""));
        assertTrue(result.contains("\"email\""));
        assertTrue(result.contains("\"name\""));
        assertTrue(result.contains("\"group\""));
        assertTrue(result.contains("\"created\""));
        assertTrue(result.contains("\"ok\""));
    }

    @Test
    void willFetchBlacklistStatusTest() throws IOException {
        String email = "john.doe@email.com";
        String url = integrationProperties.getBlacklist().getCheck().concat("/%s").formatted(email);

        Scanner s = new Scanner(streamProvider.getDataStream(url)).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"statusText\""));
        assertTrue(result.contains("\"ok\""));
    }
}