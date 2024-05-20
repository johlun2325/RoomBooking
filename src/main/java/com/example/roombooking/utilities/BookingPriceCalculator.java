package com.example.roombooking.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingPriceCalculator {

    public double totalPriceFormula(LocalDate start, LocalDate end, double roomPrice, int numberOfPeople) {
        long numberOfDaysBooked = ChronoUnit.DAYS.between(start, end);
        double totalPrice = (roomPrice * numberOfPeople) * numberOfDaysBooked;

        BigDecimal bigDecimal = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP);

        return bigDecimal.doubleValue();
    }
}
