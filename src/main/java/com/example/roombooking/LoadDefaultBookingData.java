package com.example.roombooking;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.repos.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

@ComponentScan
@RequiredArgsConstructor
public class LoadDefaultBookingData implements CommandLineRunner {

    private final TypeRepo typeRepo;
    private final RoomRepo roomRepo;
    private final CustomerRepo customerRepo;
    private final BookingRepo bookingRepo;


    @Override
    public void run(String... args) throws Exception {
        RoomType singleRoom = new RoomType("Single", 1, 0);
        RoomType largeSingleRoom = new RoomType("Large Single", 2, 1);
        RoomType doubleRoom = new RoomType("Double", 3, 1);
        RoomType largeDoubleRoom = new RoomType("Large Double", 4, 2);

        typeRepo.saveAll(List.of(singleRoom, largeSingleRoom, doubleRoom, largeDoubleRoom));

        Room room1 = new Room(995.95, singleRoom);
        Room room2 = new Room(1995.95, largeSingleRoom);
        Room room3 = new Room(2995.95, doubleRoom);
        Room room4 = new Room(3995.95, largeDoubleRoom);

        Room room5 = new Room(995.95, singleRoom);
        Room room6 = new Room(995.95, singleRoom);
        Room room7 = new Room(2995.95, doubleRoom);
        Room room8 = new Room(2995.95, doubleRoom);
        Room room9 = new Room(2995.95, doubleRoom);
        Room room10 = new Room(3995.95, largeDoubleRoom);

        roomRepo.saveAll(
                List.of(room1, room2, room3, room4, room5,
                        room6, room7, room8, room9, room10));

        Customer customer1 = new Customer("John Doe", "861023-4531", "john.doe@email.com");
        Customer customer2 = new Customer("Mary Smith", "920510-5261", "mary.smith@email.com");
        Customer customer3 = new Customer("Alice Johnson", "830105-8315", "alice.johnson@email.com");
        Customer customer4 = new Customer("Bob Williams", "770210-9873", "bob.williams@email.com");

        customerRepo.saveAll(List.of(customer1, customer2, customer3, customer4));

        Booking booking1 = new Booking(customer1, room1, 1, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 15));
        Booking booking2 = new Booking(customer2, room2, 2, LocalDate.of(2024, 2, 20), LocalDate.of(2024, 2, 25));
        Booking booking3 = new Booking(customer3, room3, 3, LocalDate.of(2024, 2, 15), LocalDate.of(2024, 2, 20));
//            Booking booking4 = new Booking(customer4, room1, 2, LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 7));

        bookingRepo.saveAll(List.of(booking1, booking2, booking3));
    }


}

