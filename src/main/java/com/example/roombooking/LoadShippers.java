package com.example.roombooking;

import com.example.roombooking.models.Shipper;
import com.example.roombooking.repos.ShipperRepo;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class LoadShippers implements CommandLineRunner {

//    private final ShipperRepo repo;

    @Override
    public void run(String... args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        Shipper[] shippers = jsonMapper.readValue(
                new URL("https://javaintegration.systementor.se/shippers"),
                Shipper[].class);

        for (Shipper s : shippers) {
            System.out.println(s.getCompanyName());
            System.out.println(s.getEmail());
//            Shipper shipper = new Shipper(s.getId(), s.getEmail(), s.getCompanyName(), s.getContactName(), s.getContactTitle(),
//            s.getStreetAddress(), s.getCity(), s.getPostalCode(), s.getCountry(), s.getPhone(), s.getFax());
//            repo.save(shipper);
        }
    }

}
