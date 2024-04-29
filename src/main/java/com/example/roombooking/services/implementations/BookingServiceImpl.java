package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    //Booking till BookingLiteDTO
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

    //Booking till BookingDTO
    @Override
    public BookingDTO convertToDto(Booking booking) {
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

    //BookingDTO till booking - kolla om funkar
    @Override
    public Booking convertDtoToBooking(BookingDTO booking) {
        Customer customer = customerRepo.findById(booking.getCustomer().getId()).orElseThrow(NoSuchElementException::new);
        Room room = roomRepo.findById(booking.getRoom().getId()).orElseThrow(NoSuchElementException::new);

        return Booking.builder()
                .id(booking.getId())
                .customer(customer)
                .room(room)
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .peek(booking -> LOGGER.info("Booking data listed: ID {}", booking.getId()))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public BookingDTO findBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(this::convertToDto)
                .orElseThrow(NoSuchElementException::new);
    }


    @Override
    public String addBooking(BookingDTO booking) {

        // TODO: Define addBooking method


        return null;
    }

    @Override
    public void updateBooking(BookingDTO booking) {
        bookingRepo.findById(booking.getId()).ifPresentOrElse(foundBooking -> {
            foundBooking.setNumberOfPeople(booking.getNumberOfPeople());
            bookingRepo.save(foundBooking);
            LOGGER.info("Booking with ID: {} updated", booking.getId());
        }, () -> LOGGER.warn("Booking with ID: {} not found", booking.getId()));
    }

    //delete by id with thymeleaf
    @Override
    public void deleteBookingById(Long id) {
        bookingRepo.findById(id).ifPresentOrElse(foundBooking -> {
            bookingRepo.delete(foundBooking);
            LOGGER.info("Booking with ID: {} deleted", id);
        }, () -> LOGGER.warn("Booking with ID: {} not found", id));
    }
}
