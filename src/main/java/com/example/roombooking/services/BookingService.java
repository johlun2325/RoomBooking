package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.MiniBookingDTO;
import com.example.roombooking.models.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface BookingService {

    Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    MiniBookingDTO bookingToMiniBookingDTO(Booking booking);
    BookingDTO bookingToBookingDTO(Booking booking);

    List<BookingDTO> getAllBookingDTOs();
    BookingDTO getBookingDTO(Long id);
}
