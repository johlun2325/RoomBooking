package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.models.Booking;

import java.util.List;

public interface BookingService {

    BookingLiteDTO convertToBookingLiteDto(Booking booking);

    BookingDTO convertToDto(Booking booking);

    BookingDTO findBookingById(Long id);

    List<BookingDTO> findAllBookings();

    Booking addBooking(BookingDTO booking);

    void deleteBookingById(Long id);

    void updateBooking(BookingDTO booking);

}
