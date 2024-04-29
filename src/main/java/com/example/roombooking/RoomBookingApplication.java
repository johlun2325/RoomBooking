package com.example.roombooking;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.repos.TypeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class RoomBookingApplication {


    public static void main(String[] args) {

        SpringApplication.run(RoomBookingApplication.class, args);

    }

    /*@Bean
    @Transactional
    public CommandLineRunner loadData(CustomerRepo customerRepo,
                                      BookingRepo bookingRepo,
                                      RoomRepo roomRepo, TypeRepo typeRepo) {
        return args -> {

            RoomType singleRoom = new RoomType("Single", 0);
            RoomType largeSingleRoom = new RoomType("Large Single", 1);
            RoomType doubleRoom = new RoomType("Double", 1);
            RoomType largeDoubleRoom = new RoomType("Large Double", 2);

            typeRepo.saveAll(List.of(singleRoom, largeSingleRoom, doubleRoom, largeDoubleRoom));

            Room room1 = new Room(995.95, singleRoom);
            Room room2 = new Room(995.95, largeSingleRoom);
            Room room3 = new Room(1995.95, doubleRoom);
            Room room4 = new Room(2995.95, largeDoubleRoom);

            roomRepo.saveAll(List.of(room1, room2, room3, room4));

            Customer customer1 = new Customer("John Doe", "861023-4531", "john.doe@email.com");
            Customer customer2 = new Customer("Mary Smith", "920510-5261", "mary.smith@email.com");
            Customer customer3 = new Customer("Alice Johnson", "830105-8315", "alice.johnson@email.com");
            Customer customer4 = new Customer("Bob Williams", "770210-9873", "bob.williams@email.com");

//            customerRepo.saveAll(List.of(customer1, customer2, customer3, customer4));

            Booking booking1 = new Booking(customer1, room1, 1, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15));
            Booking booking2 = new Booking(customer2, room2, 2, LocalDate.of(2024, 2, 20), LocalDate.of(2024, 2, 25));
            Booking booking3 = new Booking(customer3, room3, 3, LocalDate.of(2024, 3, 15), LocalDate.of(2024, 3, 20));
            Booking booking4 = new Booking(customer4, room1, 2, LocalDate.of(2024, 4, 5), LocalDate.of(2024, 4, 7));

            bookingRepo.saveAll(List.of(booking1, booking2, booking3, booking4));
        };
    } */

}
