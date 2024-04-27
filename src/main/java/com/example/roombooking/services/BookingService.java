package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.models.Booking;

import java.util.List;

public interface BookingService {

    BookingLiteDTO bookingToMiniBookingDTO(Booking booking);

    BookingDTO bookingToBookingDTO(Booking booking);

    List<BookingDTO> getAllBookingDTOs();

    BookingDTO getBookingDTO(Long id);

    
}
