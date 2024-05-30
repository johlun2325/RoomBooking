package com.example.roombooking.services.implementations;

import com.example.roombooking.models.External.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShipperServiceImplTest {

    private final StreamProvider streamProvider = mock(StreamProvider.class);
    private final ShipperRepo shipperRepo  = mock(ShipperRepo.class);
    private ShipperServiceImpl systemUnderTest;

    private static final String URL = "https://javaintegration.systementor.se/shippers";
    private static final String JSON_FILE = "shippers.json";

    @BeforeEach()
    void setUp() {
        systemUnderTest = new ShipperServiceImpl(streamProvider, shipperRepo);
    }

    @Test
    void whenGetShippersShouldMapCorrectly() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(JSON_FILE));

        Shipper[] shippers = systemUnderTest.fetchShippers();

        assertEquals(3, shippers.length);

        assertEquals(1, shippers[0].getId());
        assertEquals("birgitta.olsson@hotmail.com", shippers[0].getEmail());
        assertEquals("Svensson-Karlsson", shippers[0].getCompanyName());
        assertEquals("Erik Östlund", shippers[0].getContactName());
        assertEquals("painter", shippers[0].getContactTitle());
        assertEquals("Järnvägsallén 955", shippers[0].getStreetAddress());
        assertEquals("Gävhult", shippers[0].getCity());
        assertEquals("07427", shippers[0].getPostalCode());
        assertEquals("Sverige", shippers[0].getCountry());
        assertEquals("070-569-3764", shippers[0].getPhone());
        assertEquals("2634-25376", shippers[0].getFax());
    }

    @Test
    void fetchAndSaveShippersShouldInsertNewRecords() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(JSON_FILE));
        when(shipperRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Arrays.stream(systemUnderTest.fetchShippers()).forEach(shipper -> shipperRepo.save(shipper));
        verify(shipperRepo,times(3)).save(argThat(shipper -> shipper.getInternalId() == null));
    }

    @Test
    void fetchAndSaveShippersCorrectly() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(JSON_FILE));
        when(shipperRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Arrays.stream(systemUnderTest.fetchShippers()).forEach(shipper -> shipperRepo.save(shipper));

        verify(shipperRepo, times(1)).save(argThat(shipper ->
                shipper.getId() == 1 &&
                        shipper.getEmail().equals("birgitta.olsson@hotmail.com") &&
                        shipper.getCompanyName().equals("Svensson-Karlsson") &&
                        shipper.getContactName().equals("Erik Östlund") &&
                        shipper.getContactTitle().equals("painter") &&
                        shipper.getStreetAddress().equals("Järnvägsallén 955") &&
                        shipper.getCity().equals("Gävhult") &&
                        shipper.getPostalCode().equals("07427") &&
                        shipper.getCountry().equals("Sverige") &&
                        shipper.getPhone().equals("070-569-3764") &&
                        shipper.getFax().equals("2634-25376")
        ));

        verify(shipperRepo, times(1)).save(argThat(shipper ->
                shipper.getId() == 2 &&
                        shipper.getEmail().equals("lars.aslund@hotmail.com") &&
                        shipper.getCompanyName().equals("Änglund, Svensson AB") &&
                        shipper.getContactName().equals("Nils Östlund") &&
                        shipper.getContactTitle().equals("newsreader") &&
                        shipper.getStreetAddress().equals("Åsas Gata 15") &&
                        shipper.getCity().equals("Kristby") &&
                        shipper.getPostalCode().equals("84699") &&
                        shipper.getCountry().equals("Sverige") &&
                        shipper.getPhone().equals("073-820-0856") &&
                        shipper.getFax().equals("8238-27759")
        ));

        verify(shipperRepo, times(1)).save(argThat(shipper ->
                shipper.getId() == 3 &&
                        shipper.getEmail().equals("karin.ostlund@yahoo.com") &&
                        shipper.getCompanyName().equals("Karlsson Gruppen") &&
                        shipper.getContactName().equals("Anders Johansson") &&
                        shipper.getContactTitle().equals("dentist") &&
                        shipper.getStreetAddress().equals("Östlunds Gata 0") &&
                        shipper.getCity().equals("Faltuna") &&
                        shipper.getPostalCode().equals("85980") &&
                        shipper.getCountry().equals("Sverige") &&
                        shipper.getPhone().equals("076-869-7192") &&
                        shipper.getFax().equals("7430-900849")
        ));
    }
}
