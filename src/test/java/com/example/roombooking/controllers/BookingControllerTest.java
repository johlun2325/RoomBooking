package com.example.roombooking.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingControllerTest {

    @Autowired
    private BookingController bookingController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(bookingController).isNotNull();
    }
    @Test
    void getAllBookings() {
    }

    @Test
    void deleteBooking() {
    }

    @Test
    void updateByForm() {
    }

    @Test
    void updateBooking() {
    }

    @Test
    void testGetAllBookings() {
    }

    @Test
    void deleteCustomer() {
    }
}