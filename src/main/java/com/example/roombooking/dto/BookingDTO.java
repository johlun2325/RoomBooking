package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

    public BookingDTO(CustomerLiteDTO customer, RoomLiteDTO room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        BigDecimal price = BigDecimal.valueOf(room.getPrice());
        BigDecimal total = price.multiply(BigDecimal.valueOf(numberOfPeople));
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
