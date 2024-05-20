package com.example.roombooking.utilities;

import com.example.roombooking.models.Booking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingPriceCalculator {

    public double totalPriceFormula(LocalDate start, LocalDate end, double roomPrice, int numberOfPeople) {
        long numberOfDaysBooked = ChronoUnit.DAYS.between(start, end.plusDays(1));
        double totalPrice = (roomPrice * numberOfPeople) * numberOfDaysBooked;
        BigDecimal bigDecimal = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }

    public void applyDiscount(Booking booking, double discount, long discountedDays) {
        long totalDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate().plusDays(1));
        long fullPriceDays = totalDays - discountedDays;

        double roomPricePerPerson = booking.getRoom().getPrice() * booking.getNumberOfPeople();
        double fullPrice = roomPricePerPerson * fullPriceDays;
        double discountedPrice = roomPricePerPerson * discount * discountedDays;

        double totalPrice = fullPrice + discountedPrice;

        booking.setTotalPrice(BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

}
