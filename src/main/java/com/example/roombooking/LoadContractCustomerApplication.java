package com.example.roombooking;

import com.example.roombooking.models.ContractCustomers;
import com.example.roombooking.repos.ContractCustomerRepo;
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
public class LoadContractCustomerApplication implements CommandLineRunner {

    private final ContractCustomerRepo contractCustomerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);

    @Override
    public void run(String... args) {

        LOGGER.info("Starting to fetch contract customers from external service.");
        var xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(xmlModule);

        try {
            URL url = new URL("https://javaintegration.systementor.se/customers");
            ContractCustomers contractCustomers = xmlMapper.readValue(url, ContractCustomers.class);
            LOGGER.info("Fetched {} contract customers.", contractCustomers.getContractCustomers().size());

            long newCustomersCount = contractCustomers.getContractCustomers()
                    .stream()
                    .filter(customer -> contractCustomerRepo.findById(customer.getId()).isEmpty())
                    .peek(contractCustomerRepo::save)
                    .count();

            LOGGER.info("Saved {} new contract customers.", newCustomersCount);
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred when fetching or saving contract customers", e);
        }
    }
}
