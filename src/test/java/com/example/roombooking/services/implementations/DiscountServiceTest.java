package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.BookingRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class DiscountServiceTest {

    Booking booking = mock(Booking.class);
    BookingRepo bookingRepo = mock(BookingRepo.class);

    DiscountService discountService = new DiscountService();

    private BigDecimal calculateTotalPrice(LocalDate start, LocalDate end, BigDecimal roomPrice, int numberOfPeople) {
        long bookingDateRange = ChronoUnit.DAYS.between(start, end);
        var people = BigDecimal.valueOf(numberOfPeople);
        var days = BigDecimal.valueOf(bookingDateRange);

        return roomPrice.multiply(days).multiply(people);
    }

    private BigDecimal calculateDiscount(Booking booking, BigDecimal discountAmount, long discountedDays) {
        BigDecimal roomPricePerPerson = booking.getRoom().getPrice()
                .multiply(BigDecimal.valueOf(booking.getNumberOfPeople()));

        return roomPricePerPerson.multiply(discountAmount.multiply(BigDecimal.valueOf(discountedDays)));
    }

    @Test
    public void test() {
//
//        RoomType singleRoom = new RoomType("Single", 1, 0);
//        Customer customer1 = new Customer("John Doe", "861023-4531", "john.doe@email.com");
//
//        Room room1 = new Room(995.95, singleRoom);
//
//        Mockito.when(booking.getStartDate()).thenReturn(LocalDate.of(2024, 5, 1));
//        Mockito.when(booking.getEndDate()).thenReturn(LocalDate.of(2024, 5, 10));
//        Mockito.when(booking.getNumberOfPeople()).thenReturn(1);
//        Mockito.when(booking.getRoom()).thenReturn(room1);
//        Mockito.when(booking.getTotalPrice()).thenReturn(BigDecimal.valueOf(8963.55));
//        Mockito.when(booking.getCustomer()).thenReturn(customer1);
//
//        // Totalprice: 8963,55
//        // discount: 39,838
//
//        // after: 8923,712
//
//        discountService.applyDiscounts(booking);
//
//
//        assertEquals(BigDecimal.valueOf(8923.712), booking.getTotalPrice());
    }

}