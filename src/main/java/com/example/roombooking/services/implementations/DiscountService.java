package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.utilities.BookingPriceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Service
public class DiscountService {

    BookingPriceCalculator bookingPriceCalculator = new BookingPriceCalculator();
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);
    private static final double ZERO_POINT_FIVE_PERCENT_DISCOUNT = 0.995;
    private static final double TWO_PERCENT_DISCOUNT = 0.98;

    public void moreThanTwoDaysDiscount(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

        if (numberOfDays > 1) {
            LOGGER.info("More than two days discount applied");
            bookingPriceCalculator.applyDiscount(booking, ZERO_POINT_FIVE_PERCENT_DISCOUNT, numberOfDays - 1);
        }
    }

    public void sundayToMondayDiscount(Booking booking) {
        var numberOfWeeks = booking.getStartDate()
                .datesUntil(booking.getEndDate())
                .map(date -> date.get(WeekFields.of(new Locale("sv", "SE")).weekOfYear()))
                .distinct()
                .count();

        if (numberOfWeeks > 1) {
            LOGGER.info("Sunday to Monday discount applied");
            bookingPriceCalculator.applyDiscount(booking, TWO_PERCENT_DISCOUNT, numberOfWeeks - 1);
        }
    }

    public void annualDiscount(Booking booking) {
        long dateRange = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        long totalDays = booking.getCustomer()
                .getBookings()
                .stream()
                .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate())
                        .filter(date -> date.getYear() == LocalDate.now().getYear()))
                .count();

        if (totalDays + dateRange > 10) {
            LOGGER.info("Annual discount applied");
            bookingPriceCalculator.applyDiscount(booking, TWO_PERCENT_DISCOUNT, Math.min(dateRange, totalDays + dateRange - 10));
        }
    }

}