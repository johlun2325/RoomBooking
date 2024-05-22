package com.example.roombooking.utilities;

import com.example.roombooking.models.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingPriceCalculator {

    public BigDecimal calculateTotalPrice(LocalDate start, LocalDate end, BigDecimal roomPrice, int numberOfPeople) {
        long bookingDateRange = ChronoUnit.DAYS.between(start, end);
        var people = BigDecimal.valueOf(numberOfPeople);
        var days = BigDecimal.valueOf(bookingDateRange);

        return roomPrice.multiply(days).multiply(people);
    }
}
