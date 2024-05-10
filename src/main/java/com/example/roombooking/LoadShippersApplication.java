package com.example.roombooking;

import com.example.roombooking.models.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

@ComponentScan
@RequiredArgsConstructor
public class LoadShippersApplication implements CommandLineRunner {

    private final ShipperRepo repo;


    @Override
    public void run(String... args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        jsonMapper.registerModule(new JavaTimeModule()); //vad gör denna
        Shipper[] shippers = jsonMapper.readValue(
                new URL("https://javaintegration.systementor.se/shippers"),
                Shipper[].class);

        for (Shipper s : shippers) {
//            System.out.println(s.getCompanyName());
//            System.out.println(s.getEmail());
            Shipper shipper = new Shipper(s.getId(), s.getEmail(), s.getCompanyName(), s.getContactName(), s.getContactTitle(),
            s.getStreetAddress(), s.getCity(), s.getPostalCode(), s.getCountry(), s.getPhone(), s.getFax());
            repo.save(shipper);
        }
    }

}
