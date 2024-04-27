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
        return bookingService.getAllBookingDTOs();
    }

    @GetMapping({"/{id}"})
    BookingDTO getAllBookings(@PathVariable Long id) {
        return bookingService.getBookingDTO(id);
    }

    @DeleteMapping({"/delete"})
    BookingDTO deleteCustomer(@PathVariable Long id) {
        return bookingService.getBookingDTO(id);
    }



//    @GetMapping()
//    CollectionModel<EntityModel<Booking>> all() {
//
//        List<EntityModel<Booking>> bookings = bookingRepo.findAll().stream()
//                .map(booking -> EntityModel.of(booking,
//                        linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel(),
//                        linkTo(methodOn(BookingController.class).all()).withRel("bookings")))
//                .toList();
//
//        return CollectionModel.of(bookings, linkTo(methodOn(BookingController.class).all()).withSelfRel());
//    }
//
//    @GetMapping("/{id}")
//    EntityModel<Booking> one(@PathVariable Long id) {
//
//        Booking booking = bookingRepo.findById(id)
//                .orElseThrow(() -> {
//                    final String WARNING_MESSAGE = "No bookings with ID: %s was found".formatted(id);
//                    logger.warn(WARNING_MESSAGE);
//                    return new NoSuchElementException(WARNING_MESSAGE);
//                });
//
//        return EntityModel.of(booking,
//                linkTo(methodOn(BookingController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(BookingController.class).all()).withRel("bookings"));
//    }

    // Todo:
    //  Define and implement the behaviour of the booking functionality:
    //  - NEW BOOKING: A room can be booked by a customer for one or more nights.
    //  - RULE: A customer should not be able to book a specific room on a date where a booking already exists.
    //  - REMOVE/UPDATE: It should be possible to cancel a room or change a booking.


}
