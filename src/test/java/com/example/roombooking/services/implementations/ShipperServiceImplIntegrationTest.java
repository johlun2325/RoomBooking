package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShipperServiceImplIntegrationTest {

    @Autowired
    private ShipperRepo shipperRepo;

    @Autowired
    private StreamProvider streamProvider;

    @Autowired
    IntegrationProperties integrationProperties;

    @Autowired
    private ShipperServiceImpl systemUnderTest;

    @Test
    void fetchShippersWillFetch() throws IOException {
        systemUnderTest = new ShipperServiceImpl(streamProvider, shipperRepo, integrationProperties);
        Scanner s = new Scanner(systemUnderTest.getStreamProvider().getDataStream(integrationProperties.getShipper().getUrl()))
                .useDelimiter("\\A");

        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("\"id\""));
        assertTrue(result.contains("\"email\""));
        assertTrue(result.contains("\"companyName\""));
        assertTrue(result.contains("\"contactName\""));
        assertTrue(result.contains("\"contactTitle\""));
        assertTrue(result.contains("\"streetAddress\""));
        assertTrue(result.contains("\"postalCode\""));
        assertTrue(result.contains("\"country\""));
        assertTrue(result.contains("\"phone\""));
        assertTrue(result.contains("\"fax\""));
    }

    @Test
    void fetchAndSaveShippersShouldSaveToDatabaseTest() throws IOException {
        StreamProvider provider = mock(StreamProvider.class);
        when(provider.getDataStream(integrationProperties.getShipper().getUrl()))
                .thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        systemUnderTest = new ShipperServiceImpl(provider, shipperRepo, integrationProperties);
        shipperRepo.deleteAll();
        shipperRepo.saveAll(Arrays.asList(systemUnderTest.fetchShippers()));
        assertEquals(3, shipperRepo.count());
    }
}