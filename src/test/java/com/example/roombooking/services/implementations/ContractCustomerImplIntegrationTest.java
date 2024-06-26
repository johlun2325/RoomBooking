package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.utilities.StreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContractCustomerImplIntegrationTest {

    @Autowired
    ContractCustomerRepo contractCustomerRepo;

    @Autowired
    StreamProvider streamProvider;

    @Autowired
    IntegrationProperties integrationProperties;

    @Autowired
    ContractCustomerImpl systemUnderTest;

    @Test
    void willFetchContractCustomersTest() throws IOException {
        systemUnderTest = new ContractCustomerImpl(streamProvider, contractCustomerRepo, integrationProperties);
        Scanner s = new Scanner(systemUnderTest.getStreamProvider().getDataStream(integrationProperties.getContractCustomer().getUrl()))
                .useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        assertTrue(result.contains("<allcustomers>"));
        assertTrue(result.contains("<customers>"));
        assertTrue(result.contains("<id>"));
        assertTrue(result.contains("</id>"));
        assertTrue(result.contains("<companyName>"));
        assertTrue(result.contains("</companyName>"));
        assertTrue(result.contains("<contactName>"));
        assertTrue(result.contains("</contactName>"));
        assertTrue(result.contains("<contactTitle>"));
        assertTrue(result.contains("</contactTitle>"));
        assertTrue(result.contains("<streetAddress>"));
        assertTrue(result.contains("</streetAddress>"));
        assertTrue(result.contains("<city>"));
        assertTrue(result.contains("</city>"));
        assertTrue(result.contains("<postalCode>"));
        assertTrue(result.contains("</postalCode>"));
        assertTrue(result.contains("<country>"));
        assertTrue(result.contains("</country>"));
        assertTrue(result.contains("<phone>"));
        assertTrue(result.contains("</phone>"));
        assertTrue(result.contains("<fax>"));
        assertTrue(result.contains("</fax>"));
        assertTrue(result.contains("</customers>"));
        assertTrue(result.contains("</allcustomers>"));
    }

    @Test
    void fetchAndSaveContractCustomersShouldSaveToDatabaseTest() throws IOException {
        StreamProvider provider = mock(StreamProvider.class);
        when(provider.getDataStream(integrationProperties.getContractCustomer().getUrl()))
                .thenReturn(getClass().getClassLoader().getResourceAsStream("contractCustomers.xml"));

        systemUnderTest = new ContractCustomerImpl(provider, contractCustomerRepo, integrationProperties);
        contractCustomerRepo.deleteAll();
        contractCustomerRepo.saveAll(systemUnderTest.fetchContractCustomers());
        assertEquals(3, contractCustomerRepo.count());
    }

}