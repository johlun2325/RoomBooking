package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.models.Booking;

import java.util.List;

public interface BookingService {

    BookingLiteDTO bookingToMiniBookingDTO(Booking booking);

    BookingDTO bookingToBookingDTO(Booking booking);

    Booking bookingDTOToBooking(BookingDTO booking);

    List<BookingDTO> getAllBookingDTOs();

    BookingDTO getBookingDTO(Long id);

    //delete by id with thymeleaf
    String deleteBookingById(Long id);
    BookingDTO findBookingById(Long id);

    String addBooking(BookingDTO b);


}
