package com.example.roombooking;

import com.example.roombooking.models.ContractCustomers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
public class FetchContractCustomerApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper xmlMapper = new XmlMapper();

        ContractCustomers all = xmlMapper.readValue(
                new URL("https://javaintegration.systementor.se/customers"),
                ContractCustomers.class);

        all.getContractCustomers().forEach(System.out::println);
    }
}
