package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
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
    @Override
    public Booking convertDtoToBooking(BookingDTO booking) {
        return null;
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
