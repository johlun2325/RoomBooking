package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;

    @GetMapping()
    List<BookingDTO> getAllBookings() {
        return bookingService.findAllBookings();
    }

    @GetMapping({"/{id}"})
    BookingDTO getBooking(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @DeleteMapping({"/delete"})
    BookingDTO deleteBooking(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

//    @GetMapping()
//    CollectionModel<EntityModel<BookingDTO>> all() {
//        return bookingService.all();
//    }


//    @GetMapping("/{id}")
//    EntityModel<BookingDTO> one(@PathVariable Long id) {
//        return bookingService.one(id);
//    }

    // Todo:
    //  Define and implement the behaviour of the booking functionality:
    //  - NEW BOOKING: A room can be booked by a customer for one or more nights.
    //  - RULE: A customer should not be able to book a specific room on a date where a booking already exists.
    //  - REMOVE/UPDATE: It should be possible to cancel a room or change a booking.


}
