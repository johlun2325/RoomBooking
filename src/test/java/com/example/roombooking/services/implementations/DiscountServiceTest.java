package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.utilities.BookingPriceCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class DiscountServiceTest {

    Booking booking = mock(Booking.class);
    BookingRepo bookingRepo = mock(BookingRepo.class);

    BookingPriceCalculator bookingPriceCalculator = new BookingPriceCalculator();

    DiscountService discountService = new DiscountService();


    private static final Customer CUSTOMER = new Customer("John Doe", "900213-1234", "john.doe@example.com");
    private static final RoomType TYPE = new RoomType("Single", 1, 0);
    private static final Room ROOM = new Room(995.95, TYPE);

    private static final int NUMBER_OF_PEOPLE = 1;
    private static final LocalDate START_DATE = LocalDate.of(2024, 6, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 6, 7);


    @Test
    public void bookingPriceCalculatorAndConstructorTest() {
        Booking testBooking = new Booking(CUSTOMER, ROOM, NUMBER_OF_PEOPLE, START_DATE, END_DATE);

        assertNotNull(testBooking);
        assertEquals(CUSTOMER, testBooking.getCustomer());
        assertEquals(ROOM, testBooking.getRoom());
        assertEquals(NUMBER_OF_PEOPLE, testBooking.getNumberOfPeople());
        assertEquals(START_DATE, testBooking.getStartDate());
        assertEquals(END_DATE, testBooking.getEndDate());

        long bookingDateRange = ChronoUnit.DAYS.between(START_DATE, END_DATE);
        var people = BigDecimal.valueOf(NUMBER_OF_PEOPLE);
        var days = BigDecimal.valueOf(bookingDateRange);
        var totalPrice = testBooking.getRoom().getPrice().multiply(days).multiply(people);

        assertEquals(totalPrice, testBooking.getTotalPrice());
    }

    @Test
    public void test() {

        Mockito.when(booking.getStartDate()).thenReturn(START_DATE);
        Mockito.when(booking.getEndDate()).thenReturn(END_DATE);
        Mockito.when(booking.getNumberOfPeople()).thenReturn(NUMBER_OF_PEOPLE);
        Mockito.when(booking.getRoom()).thenReturn(ROOM);
        Mockito.when(booking.getCustomer()).thenReturn(CUSTOMER);
        Mockito.when(booking.getTotalPrice()).thenReturn(bookingPriceCalculator
                .calculateTotalPrice(START_DATE, END_DATE, ROOM.getPrice(), NUMBER_OF_PEOPLE));


        // Totalprice: 5975,7
        // More than two days discount: 24,89875
        // Sunday to Monday discount: 19.9190 SEK

        // after: 5930,88225

        discountService.applyDiscounts(booking);


    }

}