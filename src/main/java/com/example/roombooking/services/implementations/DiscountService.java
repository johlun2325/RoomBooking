package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class DiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);
    private static final double NO_DISCOUNT = 0.0;
    private static final double ZERO_POINT_FIVE_PERCENT_DISCOUNT = 0.0005;
    private static final double TWO_PERCENT_DISCOUNT = 0.02;

    private double moreThanTwoDaysDiscount(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

        if (numberOfDays >= 2) {
            LOGGER.info("More than two days discount applied");
            return ZERO_POINT_FIVE_PERCENT_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    private double sundayToMondayDiscount(Booking booking) {
        var numberOfWeeks = booking.getStartDate()
                .datesUntil(booking.getEndDate())
                .map(date -> date.get(WeekFields.of(new Locale("sv", "SE")).weekOfYear()))
                .distinct()
                .count();

        if (numberOfWeeks >= 2) {
            LOGGER.info("Sunday to Monday discount applied");
            return TWO_PERCENT_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    private double annualDiscount(Booking booking) {
        long dateRange = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

        long totalDays = booking.getCustomer()
                .getBookings()
                .stream()
                .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate())
                        .filter(date -> date.getYear() == LocalDate.now().getYear()))
                .count();

        if (totalDays + dateRange >= 11) {
            LOGGER.info("Annual discount applied");
            return TWO_PERCENT_DISCOUNT;
        }
        return NO_DISCOUNT;
    }
}