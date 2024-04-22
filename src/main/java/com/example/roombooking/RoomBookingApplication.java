package com.example.roombooking;

import com.example.roombooking.models.*;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class RoomBookingApplication {

    public static void main(String[] args) {

        SpringApplication.run(RoomBookingApplication.class, args);

    }

//    @Bean
//    public CommandLineRunner loadData(CustomerRepo customerRepo,
//                                      BookingRepo bookingRepo,
//                                      RoomRepo roomRepo) {
//        return args -> {
//            Room room1 = new Room(Type.SINGLE, 995.95);
//            Room room2 = new Room(Type.DOUBLE, 1995.95);
//            Room room3 = new Room(Type.LARGE_DOUBLE, 2995.95, 2);
//
//            roomRepo.saveAll(List.of(room1, room2, room3));
//
//            Customer customer1 = new Customer("John Doe", "861023-4531", "john.doe@email.com");
//            Customer customer2 = new Customer("Mary Smith", "920510-5261", "mary.smith@email.com");
//            Customer customer3 = new Customer("Alice Johnson", "830105-8315", "alice.johnson@email.com");
//            Customer customer4 = new Customer("Bob Williams", "770210-9873", "bob.williams@email.com");
//
//            customerRepo.saveAll(List.of(customer1, customer2, customer3, customer4));
//
//            Booking booking1 = new Booking(customer1, room1, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15));
//            Booking booking2 = new Booking(customer2, room2, LocalDate.of(2024, 2, 20), LocalDate.of(2024, 2, 25));
//            Booking booking3 = new Booking(customer3, room3, LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 20));
//
//            bookingRepo.saveAll(List.of(booking1, booking2, booking3));
//        };
//    }

}
