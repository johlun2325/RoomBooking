package com.example.roombooking;

import com.example.roombooking.models.ContractCustomers;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class FetchContractCustomerApplication implements CommandLineRunner {

    private final ContractCustomerRepo contractCustomerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchContractCustomerApplication.class);

    @Override
    public void run(String... args) {
        LOGGER.info("Starting to fetch contract customers from external service.");
        try {
            var module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            ObjectMapper xmlMapper = new XmlMapper(module);
            URL url = new URL("https://javaintegration.systementor.se/customers");

            ContractCustomers contractCustomers = xmlMapper.readValue(url, ContractCustomers.class);
            LOGGER.info("Fetched {} contract customers successfully.", contractCustomers.getContractCustomers().size());

            contractCustomerRepo.saveAll(contractCustomers.getContractCustomers());
            LOGGER.info("Contract customers have been saved to the repository successfully.");
        } catch (Exception e) {
            LOGGER.error("Error fetching or saving contract customers", e);
        }
    }
}
