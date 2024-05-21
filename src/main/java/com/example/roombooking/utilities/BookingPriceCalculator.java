package com.example.roombooking.utilities;

import com.example.roombooking.models.Booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingPriceCalculator {

    public double totalPriceFormula(LocalDate start, LocalDate end, double roomPrice, int numberOfPeople) {
        long numberOfDaysBooked = ChronoUnit.DAYS.between(start, end);
        return (roomPrice * numberOfPeople) * numberOfDaysBooked;
    }

    public void applyDiscount(Booking booking, double discount, long discountedDays) {
        long totalDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        long fullPriceDays = totalDays - discountedDays;

        double roomPricePerPerson = booking.getRoom().getPrice() * booking.getNumberOfPeople();
        double fullPrice = roomPricePerPerson * fullPriceDays;
        double discountedPrice = roomPricePerPerson * discount * discountedDays;

        double totalPrice = fullPrice + discountedPrice;

        booking.setTotalPrice(totalPrice);
    }

}
