package com.example.roombooking.services.implementations;

import com.example.roombooking.LoadContractCustomerApplication;
import com.example.roombooking.models.External.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.services.ShipperService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ShipperServiceImpl implements ShipperService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadContractCustomerApplication.class);

    @Autowired
    ShipperServiceImpl(StreamProvider streamProvider, ShipperRepo repo) {
        this.streamProvider = streamProvider;
        this.repo = repo;
    }

    StreamProvider streamProvider;

    private final ShipperRepo repo;

    @Override
    public Shipper[] fetchShippers(String url) {
        LOGGER.info("Starting to fetch shippers from external service.");

        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule());

        try {
            InputStream stream = streamProvider.getDataStream(url);
            Shipper[] shippers = jsonMapper.readValue(stream, Shipper[].class);
            LOGGER.info("Fetched {} shippers.", shippers.length);

            return shippers;

        } catch (IOException e) {
            LOGGER.error("An unexpected error occurred when fetching or saving shippers", e);
        }
        return null;
    }
}
