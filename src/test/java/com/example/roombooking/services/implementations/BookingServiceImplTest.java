package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.*;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {


    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private BookingServiceImpl service = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);

    //Customer
    private Long custId = 1L;
    private String name = "Anna";
    private String ssn = "343434-3434";
    private String email = "mockmock@mail.se";

    private Customer customer = new Customer(custId, name, ssn, email, new ArrayList<>());

    private CustomerLiteDTO customerLiteDTO = new CustomerLiteDTO().builder()
            .id(customer.getId()).name(customer.getName())
            .ssn(customer.getSsn()).email(customer.getEmail()).build();

    //Room
    private Long roomId = 2L;
    private double price = 100.0;
    private RoomType roomType = new RoomType();
    private List<Booking> bookings = new ArrayList<>();

    private Room room = new Room(roomId, price, roomType, bookings);

    private RoomLiteDTO liteRoom = new RoomLiteDTO().builder()
            .id(room.getId())
            .price(room.getPrice())
            .roomType(room.getRoomType()).build();

    //Booking
    LocalDate startDate = LocalDate.of(2024, 1, 3);
    LocalDate endDate = LocalDate.of(2024, 1, 4);
    Booking booking = new Booking(customer, room, 2, startDate, endDate);

    private BookingDTO bookingDTO = new BookingDTO().builder()
            .id(booking.getId())
            .numberOfPeople(booking.getNumberOfPeople())
            .startDate(booking.getStartDate())
            .endDate(booking.getEndDate())
            .customer(customerLiteDTO)
            .room(liteRoom).build();

    @Test
    void convertToBookingLiteDto() {
        BookingLiteDTO actual = service.convertToBookingLiteDto(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getNumberOfPeople(), booking.getNumberOfPeople());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getRoom().getId(), booking.getRoom().getId());

    }

    @Test
    void convertToDto() {

        BookingDTO actual = service.convertToDto(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getNumberOfPeople(), booking.getNumberOfPeople());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getCustomer().getId(), booking.getCustomer().getId());
        assertEquals(actual.getRoom().getId(), booking.getRoom().getId());

    }


    //funkar ej än
    @Test
    void convertDtoToBooking() {
        BookingServiceImpl serv = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);

        Booking actual = serv.convertDtoToBooking(bookingDTO); ///err

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getNumberOfPeople(), booking.getNumberOfPeople());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());
        assertEquals(actual.getRoom().getId(), booking.getRoom().getId());
    }

    @Test
    void findAllBookings() {

        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));
        BookingServiceImpl serv = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);

        List<BookingDTO> allCustomers = serv.findAllBookings();
        assertEquals(1, allCustomers.size());

    }

    @Test
    void findBookingById() {

        when(bookingRepo.findById(booking.getId())).thenReturn(Optional.of(booking));
        BookingServiceImpl serv = new BookingServiceImpl(bookingRepo, customerRepo, roomRepo);
        BookingDTO foundBooking = serv.findBookingById(booking.getId());

        assertEquals(booking.getId(), foundBooking.getId());
        assertEquals(booking.getStartDate(), foundBooking.getStartDate());
        assertEquals(booking.getEndDate(), foundBooking.getEndDate());

    }

    @Test
    void addBooking() {
    }

    @Test
    void updateBooking() {
    }

    @Test
    void deleteBookingById() {
    }
}