package com.example.roombooking.controllers;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.BookingRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/bookings")
class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingRepo bookingRepo;

    public BookingController(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @GetMapping()
    CollectionModel<EntityModel<Booking>> all() {

        List<EntityModel<Booking>> bookings = bookingRepo.findAll().stream()
                .map(booking -> EntityModel.of(booking,
                        linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel(),
                        linkTo(methodOn(BookingController.class).all()).withRel("bookings")))
                .toList();

        return CollectionModel.of(bookings, linkTo(methodOn(BookingController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    EntityModel<Booking> one(@PathVariable Long id) {

        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> {
                    final String WARNING_MESSAGE = "No bookings with ID: %s was found".formatted(id);
                    logger.warn(WARNING_MESSAGE);
                    return new NoSuchElementException(WARNING_MESSAGE);
                });

        return EntityModel.of(booking,
                linkTo(methodOn(BookingController.class).one(id)).withSelfRel(),
                linkTo(methodOn(BookingController.class).all()).withRel("bookings"));
    }

    // Todo:
    //  Define and implement the behaviour of the booking functionality:
    //  - NEW BOOKING: A room can be booked by a customer for one or more nights.
    //  - RULE: A customer should not be able to book a specific room on a date where a booking already exists.
    //  - REMOVE/UPDATE: It should be possible to cancel a room or change a booking.


}
