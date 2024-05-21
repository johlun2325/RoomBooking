package com.example.roombooking.services;

import com.example.roombooking.repos.ShipperRepo;
import com.example.roombooking.services.implementations.ShipperServiceImpl;
import com.example.roombooking.services.implementations.StreamProvider;
import com.example.roombooking.services.implementations.StreamProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@SpringBootTest
class ShipperServiceIntegrationTest {

    @Autowired
    private ShipperRepo shipperRepo;
    @Autowired
    private StreamProvider streamProvider;

    private ShipperService sut;


    @Test
    public void fetchShippers() {
        sut = new ShipperServiceImpl(streamProvider,shipperRepo);
//        Scanner s = new Scanner(sut.getDataStream()).useDelimiter("\\A");
//        String result = s.hasNext() ? s.next() : "";
    }


}