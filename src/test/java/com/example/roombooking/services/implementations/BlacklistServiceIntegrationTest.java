package com.example.roombooking.services.implementations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlacklistServiceIntegrationTest {

    @Autowired
    StreamProvider streamProvider;

    @Test
    void willFetchBlacklistedCustomersTest() throws IOException {
        Scanner s = new Scanner(streamProvider.getDataStream("https://javabl.systementor.se/api/jeri/blacklist")).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("name"));
        assertTrue(result.contains("group"));
        assertTrue(result.contains("created"));
        assertTrue(result.contains("ok"));
    }

    @Test
    void willFetchBlacklistStatusTest() throws IOException {
        final String EMAIL = "john.doe@email.com";
        final String URL = "https://javabl.systementor.se/api/jeri/blacklistcheck/%s".formatted(EMAIL);

        Scanner s = new Scanner(streamProvider.getDataStream(URL)).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("statusText"));
        assertTrue(result.contains("ok"));
    }
}