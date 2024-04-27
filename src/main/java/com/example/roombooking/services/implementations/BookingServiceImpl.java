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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public BookingLiteDTO bookingToMiniBookingDTO(Booking booking) {
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
    public BookingDTO bookingToBookingDTO(Booking booking) {
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
    public List<BookingDTO> getAllBookingDTOs() {
        return bookingRepo.findAll()
                .stream()
                .map(this::bookingToBookingDTO)
                .peek(booking -> LOGGER.info("Booking data listed: ID %S".formatted(booking.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public BookingDTO getBookingDTO(Long id) {
        return bookingRepo.findById(id)
                .map(this::bookingToBookingDTO)
                .orElseThrow(NoSuchElementException::new);
    }


}
