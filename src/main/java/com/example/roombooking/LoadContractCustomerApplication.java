package com.example.roombooking;

import com.example.roombooking.models.ContractCustomer;
import com.example.roombooking.models.ContractCustomers;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.List;

@ComponentScan
@RequiredArgsConstructor
public class LoadContractCustomerApplication implements CommandLineRunner {

    private final ContractCustomerRepo repo;
    private final ContractCustomerService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);

    @Override
    public void run(String... args) {

        List<ContractCustomer> customers = service.fetchContractCustomers();

        if (customers != null) {

            long newCustomersCount = customers
                    .stream()
                    .filter(customer -> repo.findById(customer.getId()).isEmpty())
                    .peek(repo::save)
                    .count();

            LOGGER.info("Saved {} new contract customers.", newCustomersCount);

        }
        else {
            LOGGER.info("No customers were found");
        }
    }
}
