package com.example.roombooking.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingPriceCalculator {

    private static final double PRICE_PER_DAY = 50;

    public double totalPriceFormula(LocalDate start, LocalDate end, double roomPrice) {
        long numberOfDaysBooked = ChronoUnit.DAYS.between(start, end);
        double result = roomPrice + (numberOfDaysBooked * PRICE_PER_DAY);

        BigDecimal bigDecimal = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
