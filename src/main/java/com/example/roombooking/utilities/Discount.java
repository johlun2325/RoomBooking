package com.example.roombooking.utilities;

import com.example.roombooking.models.Booking;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.function.Function;

@Service
public interface Discount extends Function<Booking, BigDecimal> {

    BigDecimal ZERO_POINT_FIVE_PERCENT_DISCOUNT = BigDecimal.valueOf(0.005);
    BigDecimal TWO_PERCENT_DISCOUNT = BigDecimal.valueOf(0.02);

    default Discount combine(Discount after) {
        return booking -> {
            BigDecimal discount1 = this.apply(booking);
            BigDecimal discount2 = after.apply(booking);
            return discount1.add(discount2);
        };

//        var disc = Discount.multiNightDiscount()
//                .combine(Discount.sundayToMondayDiscount())
//                .combine(Discount.loyalCustomerDiscount())
//                .apply(newBooking);

    }

    private static BigDecimal calculateDiscount(Booking booking, BigDecimal discountAmount, long discountedDays) {
        BigDecimal roomPricePerPerson = booking.getRoom().getPrice()
                .multiply(BigDecimal.valueOf(booking.getNumberOfPeople()));

        return roomPricePerPerson.multiply(discountAmount.multiply(BigDecimal.valueOf(discountedDays)));
    }

    static Discount multiNightDiscount() {
        return booking -> {
            long numberOfDays = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

            if (numberOfDays > 1) {
//            LOGGER.info("More than two days discount applied");
                return calculateDiscount(booking, ZERO_POINT_FIVE_PERCENT_DISCOUNT, numberOfDays - 1);
            }
            return BigDecimal.ZERO;
        };
    }

    static Discount sundayToMondayDiscount() {
        return booking -> {
            long numberOfWeeks = booking.getStartDate()
                    .datesUntil(booking.getEndDate())
                    .map(date -> date.get(WeekFields.of(new Locale("sv", "SE")).weekOfYear()))
                    .distinct()
                    .count();

            if (numberOfWeeks > 1) {
//            LOGGER.info("Sunday to Monday discount applied");
                return calculateDiscount(booking, TWO_PERCENT_DISCOUNT, numberOfWeeks - 1);
            }
            return BigDecimal.ZERO;
        };
    }

    static Discount loyalCustomerDiscount() {
        return booking -> {
            long dateRange = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
            long totalDays = booking.getCustomer()
                    .getBookings()
                    .stream()
                    .flatMap(b -> b.getStartDate().datesUntil(b.getEndDate())
                            .filter(date -> date.getYear() == LocalDate.now().getYear()))
                    .count();

            if (totalDays + dateRange > 10) {
//            LOGGER.info("Annual discount applied");
                return calculateDiscount(booking, TWO_PERCENT_DISCOUNT, (Math.min(dateRange, totalDays + dateRange - 10)));
            }
            return BigDecimal.ZERO;
        };
    }
}
