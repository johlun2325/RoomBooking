package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

    private Long id;
    private CustomerLiteDTO customer;
    private RoomLiteDTO room;
    private int numberOfPeople;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;

    public BookingDTO(CustomerLiteDTO customer, RoomLiteDTO room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPriceFormula();
    }

    private double totalPriceFormula() {
        long numberOfDaysBooked = ChronoUnit.DAYS.between(startDate, endDate);
        double roomPriceTimesPeopleCount = room.getPrice() * numberOfPeople;
        final double TWENTY_PROCENT = 0.2;
        double pricePerDay = room.getPrice() * TWENTY_PROCENT;

        double result = roomPriceTimesPeopleCount + (pricePerDay * numberOfDaysBooked);
        BigDecimal bigDecimal = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
