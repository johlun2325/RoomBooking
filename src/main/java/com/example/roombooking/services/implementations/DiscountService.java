package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.stream.Stream;

@Service
public class DiscountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountService.class);
    private static final BigDecimal ZERO_POINT_FIVE_PERCENT_DISCOUNT = BigDecimal.valueOf(0.005);
    private static final BigDecimal TWO_PERCENT_DISCOUNT = BigDecimal.valueOf(0.02);

    private BigDecimal calculateDiscount(Booking booking, BigDecimal discountAmount, long discountedDays) {
        BigDecimal roomPricePerPerson = booking.getRoom().getPrice()
                .multiply(BigDecimal.valueOf(booking.getNumberOfPeople()));

        return roomPricePerPerson.multiply(discountAmount.multiply(BigDecimal.valueOf(discountedDays)));
    }

    private BigDecimal multiNightDiscount(Booking booking) {
        long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate().plusDays(1), booking.getEndDate().plusDays(1));

        if (numberOfDays > 1) {
            BigDecimal reduction = calculateDiscount(booking, ZERO_POINT_FIVE_PERCENT_DISCOUNT, numberOfDays - 1);
            LOGGER.info("More than two days discount applied, amount: {} SEK", reduction);
            return reduction;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal sundayToMondayDiscount(Booking booking) {
        long numberOfWeeks = booking.getStartDate().plusDays(1).datesUntil(booking.getEndDate().plusDays(1))
                .map(date -> date.get(WeekFields.of(new Locale("sv", "SE")).weekOfYear()))
                .distinct()
                .count();

        if (numberOfWeeks > 1) {
            BigDecimal reduction = calculateDiscount(booking, TWO_PERCENT_DISCOUNT, numberOfWeeks - 1);
            LOGGER.info("Sunday to Monday discount applied, amount: {} SEK", reduction);
            return reduction;
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal loyalCustomerDiscount(Booking booking) {
        long dateRange = ChronoUnit.DAYS.between(booking.getStartDate().plusDays(1), booking.getEndDate().plusDays(1));
        long totalDays = booking.getCustomer()
                .getBookings()
                .stream()
                .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate())
                        .filter(date -> date.getYear() == LocalDate.now().getYear()))
                .count();

        if (totalDays + dateRange > 10) {
            BigDecimal reduction = calculateDiscount(booking, TWO_PERCENT_DISCOUNT, (Math.min(dateRange, totalDays + dateRange - 10)));
            LOGGER.info("Annual discount applied, amount: {} SEK", reduction);
            return reduction;
        }
        return BigDecimal.ZERO;
    }

    public void applyDiscounts(Booking booking) {
        BigDecimal totalDiscount = Stream.of(
                        multiNightDiscount(booking),
                        loyalCustomerDiscount(booking),
                        sundayToMondayDiscount(booking))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        booking.setDiscount(totalDiscount);
    }
}