package com.example.roombooking;

import com.example.roombooking.models.BusinessCustomers;
import com.example.roombooking.repos.BusinessCustomerRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class FetchContractCustomerApplication implements CommandLineRunner {

    private final BusinessCustomerRepo businessCustomerRepo;

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper xmlMapper = new XmlMapper();

        businessCustomerRepo.saveAll(xmlMapper.readValue(
                    new URL("https://javaintegration.systementor.se/customers"),
                    BusinessCustomers.class)
                .getBusinessCustomers());
    }
}
