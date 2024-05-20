package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.utilities.BookingPriceCalculator;
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

    BookingPriceCalculator bookingPriceCalculator = new BookingPriceCalculator();
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);
    private static final double ZERO_POINT_FIVE_PERCENT_DISCOUNT = 0.995;
    private static final double TWO_PERCENT_DISCOUNT = 0.98;

    private void moreThanTwoDaysDiscount(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate().plusDays(1));

        if (numberOfDays > 1) {
            LOGGER.info("More than two days discount applied");
            bookingPriceCalculator.applyDiscount(booking, ZERO_POINT_FIVE_PERCENT_DISCOUNT, numberOfDays - 1);
        }
    }

    private void sundayToMondayDiscount(Booking booking) {
        var numberOfWeeks = booking.getStartDate()
                .datesUntil(booking.getEndDate().plusDays(1))
                .map(date -> date.get(WeekFields.of(new Locale("sv", "SE")).weekOfYear()))
                .distinct()
                .count();

        if (numberOfWeeks > 1) {
            LOGGER.info("Sunday to Monday discount applied");
            bookingPriceCalculator.applyDiscount(booking, TWO_PERCENT_DISCOUNT, numberOfWeeks - 1);
        }
    }


    private void annualDiscount(Booking booking) {
        long dateRange = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate().plusDays(1));
        long totalDays = booking.getCustomer()
                .getBookings()
                .stream()
                .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate().plusDays(1))
                        .filter(date -> date.getYear() == LocalDate.now().getYear()))
                .count();

        // TODO: Need to return right number of days to be discounted
        if (dateRange + totalDays > 10) {
            LOGGER.info("Annual discount applied");
            bookingPriceCalculator.applyDiscount(booking, TWO_PERCENT_DISCOUNT,
                    Math.min(dateRange + totalDays - 10, dateRange));
        }
    }

    public void applyDiscounts(Booking booking) {
        moreThanTwoDaysDiscount(booking);
        sundayToMondayDiscount(booking);
        annualDiscount(booking);
    }
}