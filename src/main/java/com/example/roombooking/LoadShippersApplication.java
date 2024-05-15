package com.example.roombooking;

import com.example.roombooking.models.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.services.ShipperService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.Arrays;

@ComponentScan
@RequiredArgsConstructor
public class LoadShippersApplication implements CommandLineRunner {

    private final ShipperRepo shipperRepo;
    private final ShipperService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);
    String url = "https://javaintegration.systementor.se/shippers";

    @Override
    public void run(String... args) {

        Shipper[] shippers = service.fetchShippers(url);

        if (shippers != null) {

            long newShippersCount = Arrays.stream(shippers)
                    .filter(shipper -> shipperRepo.findById(shipper.getId()).isEmpty())
                    .peek(shipperRepo::save)
                    .count();

            LOGGER.info("Saved {} new shippers", newShippersCount);

        }
        else {
            LOGGER.info("No shippers were found");
        }
    }
}
