package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class DiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);

    private static final double ZERO_POINT_FIVE_PERCENT_DISCOUNT = 0.9995;
    private static final double TWO_PERCENT_DISCOUNT = 0.98;

    private void moreThanTwoDaysDiscount(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

        if (numberOfDays >= 2) {
            LOGGER.info("More than two days discount applied");
            booking.setTotalPrice(ZERO_POINT_FIVE_PERCENT_DISCOUNT * booking.getTotalPrice());
        }
    }

    // TODO: NOT CORRECT!
    private void sundayToMondayDiscount(Booking booking) {
        long numberOfWeeks = ChronoUnit.WEEKS.between(booking.getStartDate(), booking.getEndDate());

        if (numberOfWeeks >= 1) {
            LOGGER.info("Sunday to Monday discount applied");
            booking.setTotalPrice(TWO_PERCENT_DISCOUNT * booking.getTotalPrice());
        }
    }

    private void annualDiscount(Booking booking) {
        long totalDays = booking.getCustomer()
                .getBookings()
                .stream()
                .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate())
                        .filter(date -> date.getYear() == LocalDate.now().getYear()))
                .count();

        if (totalDays > 10) {
            LOGGER.info("Annual discount applied");
            booking.setTotalPrice(TWO_PERCENT_DISCOUNT * booking.getTotalPrice());
        }
    }

    // TODO: NOT CORRECT! Since we are using the booking parameter (old totalPrice) and not affecting the price dynamically
    public void applyDiscount(Booking booking) {
        moreThanTwoDaysDiscount(booking);
        sundayToMondayDiscount(booking);
        annualDiscount(booking);

        booking.setTotalPrice(BigDecimal.valueOf(booking.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }

}