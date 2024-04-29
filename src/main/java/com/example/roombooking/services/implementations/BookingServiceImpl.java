package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.services.CustomerService;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

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

    @Override
    public String addBooking(BookingDTO booking) {

        // När vi har kommit hit, så har det bara listats tillgängliga rum (via sökningen: datum och antal personer)

        var message = new AtomicReference<String>();
        return bookingRepo.findById(booking.getId())
                .map(foundBooking -> {
                    message.set("Booking with ID: %s exists".formatted(booking.getId()));
                    LOGGER.warn(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("Booking with ID: %s added".formatted(booking.getId()));
                    LOGGER.info(message.get());
                    return message.get();
                });
    }

    @Override
    public String deleteBooking(BookingDTO booking) {
        return "";
    }

    @Override
    public String updateBooking(BookingDTO booking) {
        return "";
    }

}
