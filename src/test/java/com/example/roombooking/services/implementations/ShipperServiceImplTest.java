package com.example.roombooking.services.implementations;

import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.services.ShipperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShipperServiceImplTest {

    @Autowired
    private ShipperRepo shipperRepo;
    @Autowired
    private StreamProvider streamProvider;

    private ShipperService sut;
    private final String url = "";

    @Test
    public void fetchShippers() {
        //unit test
    }


}