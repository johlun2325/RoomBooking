package com.example.roombooking.services.implementations;

import com.example.roombooking.models.External.ContractCustomer;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContractCustomerImplTest {

    private final StreamProvider streamProvider = mock(StreamProvider.class);
    private final ContractCustomerRepo contractCustomerRepo  = mock(ContractCustomerRepo.class);
    private ContractCustomerImpl systemUnderTest;

    private static final String URL = "https://javaintegration.systementor.se/customers";
    private static final String XML_FILE = "contractCustomers.xml";

    @BeforeEach()
    void setUp() {
        systemUnderTest = new ContractCustomerImpl(streamProvider, contractCustomerRepo);
    }

    @Test
    void fetchingContractCustomersShouldMapCorrectly() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(XML_FILE));
        List<ContractCustomer> result = systemUnderTest.fetchContractCustomers();

        assertEquals(3, result.size());

        assertEquals(1, result.get(0).getId());
        assertEquals("Persson Kommanditbolag", result.get(0).getCompanyName());
        assertEquals("Maria Åslund", result.get(0).getContactName());
        assertEquals("gardener", result.get(0).getContactTitle());
        assertEquals("Anderssons Gata 259", result.get(0).getStreetAddress());
        assertEquals("Kramland", result.get(0).getCity());
        assertEquals(60843, result.get(0).getPostalCode());
        assertEquals("Sverige", result.get(0).getCountry());
        assertEquals("076-340-7143", result.get(0).getPhone());
        assertEquals("1500-16026", result.get(0).getFax());

        assertEquals(2, result.get(1).getId());
        assertEquals("Karlsson-Eriksson", result.get(1).getCompanyName());
        assertEquals("Jörgen Gustafsson", result.get(1).getContactName());
        assertEquals("philosopher", result.get(1).getContactTitle());
        assertEquals("Undre Villagatan 451", result.get(1).getStreetAddress());
        assertEquals("Alingtorp", result.get(1).getCity());
        assertEquals(28838, result.get(1).getPostalCode());
        assertEquals("Sverige", result.get(1).getCountry());
        assertEquals("070-369-5518", result.get(1).getPhone());
        assertEquals("7805-209976", result.get(1).getFax());

        assertEquals(3, result.get(2).getId());
        assertEquals("Eriksson Group", result.get(2).getCompanyName());
        assertEquals("Anna Karlsson", result.get(2).getContactName());
        assertEquals("journalist", result.get(2).getContactTitle());
        assertEquals("Johanssons Väg 036", result.get(2).getStreetAddress());
        assertEquals("Arlöv", result.get(2).getCity());
        assertEquals(77616, result.get(2).getPostalCode());
        assertEquals("Sverige", result.get(2).getCountry());
        assertEquals("076-904-2433", result.get(2).getPhone());
        assertEquals("8653-585976", result.get(2).getFax());
    }

    @Test
    void fetchAndSaveContractCustomerShouldInsertNewRecords() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(XML_FILE));
        when(contractCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        systemUnderTest.fetchContractCustomers().forEach(contractCustomer -> contractCustomerRepo.save(contractCustomer));
        verify(contractCustomerRepo,times(3)).save(argThat(contractCustomer -> contractCustomer.getLocalId() == null));
    }

    @Test
    void fetchAndSaveContractCustomersCorrectly() throws IOException {
        when(streamProvider.getDataStream(URL)).thenReturn(getClass().getClassLoader().getResourceAsStream(XML_FILE));
        when(contractCustomerRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        systemUnderTest.fetchContractCustomers().forEach(contractCustomer -> contractCustomerRepo.save(contractCustomer));

        verify(contractCustomerRepo, times(1)).save(argThat(customer ->
                customer.getId() == 1 &&
                        customer.getCompanyName().equals("Persson Kommanditbolag") &&
                        customer.getContactName().equals("Maria Åslund") &&
                        customer.getContactTitle().equals("gardener") &&
                        customer.getStreetAddress().equals("Anderssons Gata 259") &&
                        customer.getCity().equals("Kramland") &&
                        customer.getPostalCode() == 60843 &&
                        customer.getCountry().equals("Sverige") &&
                        customer.getPhone().equals("076-340-7143") &&
                        customer.getFax().equals("1500-16026")
        ));

        verify(contractCustomerRepo, times(1)).save(argThat(customer ->
                customer.getId() == 2 &&
                        customer.getCompanyName().equals("Karlsson-Eriksson") &&
                        customer.getContactName().equals("Jörgen Gustafsson") &&
                        customer.getContactTitle().equals("philosopher") &&
                        customer.getStreetAddress().equals("Undre Villagatan 451") &&
                        customer.getCity().equals("Alingtorp") &&
                        customer.getPostalCode() == 28838 &&
                        customer.getCountry().equals("Sverige") &&
                        customer.getPhone().equals("070-369-5518") &&
                        customer.getFax().equals("7805-209976")
        ));

        verify(contractCustomerRepo, times(1)).save(argThat(customer ->
                customer.getId() == 3 &&
                        customer.getCompanyName().equals("Eriksson Group") &&
                        customer.getContactName().equals("Anna Karlsson") &&
                        customer.getContactTitle().equals("journalist") &&
                        customer.getStreetAddress().equals("Johanssons Väg 036") &&
                        customer.getCity().equals("Arlöv") &&
                        customer.getPostalCode() == 77616 &&
                        customer.getCountry().equals("Sverige") &&
                        customer.getPhone().equals("076-904-2433") &&
                        customer.getFax().equals("8653-585976")
        ));
    }
}