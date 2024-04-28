package com.example.roombooking.services;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.models.Booking;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface BookingService {

    BookingLiteDTO convertToBookingLiteDto(Booking booking);

    BookingDTO convertDtoToBooking(Booking booking);

    Booking convertDtoToBooking(BookingDTO booking);

    List<BookingDTO> findAllBookings();

    BookingDTO findBookingById(Long id);

    // HATEOAS: Not used
    CollectionModel<EntityModel<BookingDTO>> all();

    // HATEOAS: Not used
    EntityModel<BookingDTO> one(Long id);
}
