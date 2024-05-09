package com.example.roombooking;

import com.example.roombooking.models.ContractCustomers;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class FetchContractCustomerApplication implements CommandLineRunner {

    private final ContractCustomerRepo contractCustomerRepo;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper xmlMapper = new XmlMapper();

        contractCustomerRepo.saveAll(xmlMapper.readValue(
                    new URL("https://javaintegration.systementor.se/customers"),
                    ContractCustomers.class)
                .getContractCustomers());
    }
}
