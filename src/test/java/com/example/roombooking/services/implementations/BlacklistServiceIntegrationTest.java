package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

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
        final String EMAIL = "john.doe@email.com";
        final String URL = "https://javabl.systementor.se/api/jeri/blacklistcheck/%s".formatted(EMAIL);

        Scanner s = new Scanner(streamProvider.getDataStream(URL)).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"statusText\""));
        assertTrue(result.contains("\"ok\""));
    }
}