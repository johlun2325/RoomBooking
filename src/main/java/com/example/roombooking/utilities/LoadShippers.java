package com.example.roombooking.utilities;

import com.example.roombooking.models.Shipper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class LoadShippers implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        Shipper[] shippers = jsonMapper.readValue(
                new URL("https://javaintegration.systementor.se/shippers"),
                Shipper[].class);

        for (Shipper s : shippers) {
            System.out.println(s.getCompanyName());
            System.out.println(s.getEmail());
        }
    }

}
