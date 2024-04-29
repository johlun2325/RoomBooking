package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
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

}
