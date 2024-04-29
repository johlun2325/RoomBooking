package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.*;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.services.CustomerService;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Interval;
import org.hibernate.internal.util.compare.CalendarComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerService customerService;
    private final RoomService roomService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public BookingLiteDTO convertToBookingLiteDto(Booking booking) {
        return BookingLiteDTO.builder()
                .id(booking.getId())
                .room(new RoomLiteDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public BookingDTO convertDtoToBooking(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .customer(new CustomerLiteDTO(
                        booking.getCustomer().getId(),
                        booking.getCustomer().getName(),
                        booking.getCustomer().getSsn(),
                        booking.getCustomer().getEmail()))
                .room(new RoomLiteDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::convertDtoToBooking)
                .peek(booking -> LOGGER.info("Booking data listed: ID %S".formatted(booking.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public BookingDTO findBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(this::convertDtoToBooking)
                .orElseThrow(NoSuchElementException::new);
    }

    /*
    private Long id;
    private Customer customer;

    private Room room;

    private int numberOfPeople;
    private LocalDate startDate;
    private LocalDate endDate;
     */
    // Usage: When a new customer creates a new booking!
    @Override
    public Booking convertDtoToBooking(BookingDTO booking) {
        return Booking.builder()
                .id(booking.getId())
                .customer(customerService.convertLiteDtoToCustomer(booking.getCustomer()))
                .room(roomService.convertLiteDtoToRoom(booking.getRoom()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    /*
    private Long id;
    private String name;
    private String ssn;
    private String email;
    private List<Booking> bookings;
     */
    @Override
    public String addBooking(BookingDTO booking) {

        // När vi har kommit hit... har det bara visats tillgängliga rum (Datum och antal personer)

        var existingBooking = bookingRepo.findById(booking.getId());

        if (existingBooking.isPresent()) {
            return "Booking already exists";
        }

        var roomType = booking.getRoom().getRoomType();


        var customerExists = customerService.findCustomerById(booking.getCustomer().getId());

        if (customerExists.getId() == null) {

        }


        return "New booking added";
//        var message = new AtomicReference<String>();
//        return bookingRepo.findById(booking.getId())
//                .map(foundBooking -> {
//                    message.set("Booking with ID: %s exists".formatted(booking.getId()));
//                    LOGGER.warn(message.get());
//                    return message.get();
//                })
//                .orElseGet(() -> {
//                    message.set("Booking with ID: %s added".formatted(booking.getId()));
//                    LOGGER.info(message.get());
//                    return message.get();
//                });
    }

    @Override
    public String deleteBooking(BookingDTO booking) {
        return "";
    }

    @Override
    public String updateBooking(BookingDTO booking) {
        return "";
    }

    // HATEOAS: Not used
    @Override
    public CollectionModel<EntityModel<BookingDTO>> all() {
        List<EntityModel<BookingDTO>> bookings = findAllBookings().stream()
                .map(booking -> EntityModel.of(booking,
                        linkTo(methodOn(BookingServiceImpl.class).one(booking.getId())).withSelfRel(),
                        linkTo(methodOn(BookingServiceImpl.class).all()).withRel("bookings")))
                .toList();

        return CollectionModel.of(bookings, linkTo(methodOn(BookingServiceImpl.class).all()).withSelfRel());
    }

    // HATEOAS: Not used
    @Override
    public EntityModel<BookingDTO> one(Long id) {
        BookingDTO booking = findBookingById(id);

        return EntityModel.of(booking,
                linkTo(methodOn(BookingServiceImpl.class).one(id)).withSelfRel(),
                linkTo(methodOn(BookingServiceImpl.class).all()).withRel("bookings"));
    }

}
