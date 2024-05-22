package com.example.roombooking;

import com.example.roombooking.models.External.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.services.ShipperService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan
@RequiredArgsConstructor
public class LoadShippersApplication implements CommandLineRunner {

    private final ShipperRepo shipperRepo;
    private final ShipperService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);
    private final String URL = "https://javaintegration.systementor.se/shippers";

    @Override
    public void run(String... args) {

        Shipper[] shippers = service.fetchShippers(URL);

        if (shippers != null) {
            long newShippersCount = Arrays.stream(shippers)
                    .filter(shipper -> shipperRepo.findById(shipper.getId()).isEmpty())
                    .peek(shipperRepo::save)
                    .count();

            LOGGER.info("Saved {} new shippers", newShippersCount);
        } else {
            LOGGER.info("No shippers were found");
        }
    }
}
