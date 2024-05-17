package com.example.roombooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class RoomBookingApplication {

    public static void main(String[] args) {

        if (args.length == 0) {
            SpringApplication.run(RoomBookingApplication.class, args);
        } else if (Objects.equals(args[0], "loadShippersApp")) {
            SpringApplication application = new SpringApplication(LoadShippersApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "loadContractCustomerApp")) {
            SpringApplication application = new SpringApplication(LoadContractCustomerApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "loadMessagesApp")) {
            SpringApplication application = new SpringApplication(LoadMessagesApplication.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "loadDefaultBookingData")) {
            SpringApplication application = new SpringApplication(LoadDefaultBookingData.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }
}
