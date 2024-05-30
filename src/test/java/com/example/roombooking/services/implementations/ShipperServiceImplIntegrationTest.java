package com.example.roombooking.services.implementations;

import com.example.roombooking.repos.ShipperRepo;
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

    private ShipperServiceImpl sut;
    String url = "https://javaintegration.systementor.se/shippers";

    @Test
    public void fetchShippersWillFetch() throws IOException {
        sut = new ShipperServiceImpl(streamProvider, shipperRepo);
        Scanner s = new Scanner(sut.streamProvider.getDataStream(url)).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(  result.contains("\"id\"") );
        assertTrue(  result.contains("\"email\"") );
        assertTrue(  result.contains("\"companyName\"") );
        assertTrue(  result.contains("\"contactName\"") );
        assertTrue(  result.contains("\"contactTitle\"") );
        assertTrue(  result.contains("\"streetAddress\"") );
        assertTrue(  result.contains("\"postalCode\"") );
        assertTrue(  result.contains("\"country\"") );
        assertTrue(  result.contains("\"phone\"") );
        assertTrue(  result.contains("\"fax\"") );
    }

    @Test
    void fetchAndSaveShippersShouldSaveToDatabaseTest() throws IOException {
        final String URL = "https://javaintegration.systementor.se/shippers";
        StreamProvider provider = mock(StreamProvider.class);
        when(provider.getDataStream(URL))
                .thenReturn(getClass().getClassLoader().getResourceAsStream("shippers.json"));

        sut = new ShipperServiceImpl(provider, shipperRepo);
        shipperRepo.deleteAll();
        shipperRepo.saveAll(Arrays.asList(sut.fetchShippers(URL)));
        assertEquals(3, shipperRepo.count());
    }
}