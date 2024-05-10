package com.example.roombooking;

import com.example.roombooking.models.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@ComponentScan
@RequiredArgsConstructor
public class LoadShippersApplication implements CommandLineRunner {

    private final ShipperRepo shipperRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);


    @Override
    public void run(String... args) {
        LOGGER.info("Starting to fetch shippers from external service.");

        try {
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule()); //vad gör denna
            Shipper[] shippers;
            shippers = jsonMapper.readValue(
                    new URL("https://javaintegration.systementor.se/shippers"),
                    Shipper[].class);
            LOGGER.info("Fetched {} shippers successfully.", shippers.length);

            shipperRepo.saveAll(Arrays.asList(shippers));

            LOGGER.info("Shippers have been saved to the repository successfully.");

        } catch (Exception e) {
            LOGGER.error("Error fetching or saving shippers", e);
        }

    }

}
