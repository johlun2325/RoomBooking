package com.example.roombooking;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
@EnableConfigurationProperties(IntegrationProperties.class)
public class RoomBookingApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;

    @Bean
    CommandLineRunner commandLineRunner() {
        return arg -> userDataSeeder.seed();
    }

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
