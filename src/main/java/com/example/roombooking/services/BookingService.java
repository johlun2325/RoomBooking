package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.models.Booking;

import java.util.List;

public interface BookingService {

    BookingLiteDTO convertToBookingLiteDto(Booking booking);

    BookingDTO convertToDto(Booking booking);

    Booking convertDtoToBooking(BookingDTO booking);

    BookingDTO findBookingById(Long id);

    List<BookingDTO> findAllBookings();

    String addBooking(BookingDTO booking);

    //delete by id with thymeleaf
    void deleteBookingById(Long id);

    void updateBooking(BookingDTO booking);

}
