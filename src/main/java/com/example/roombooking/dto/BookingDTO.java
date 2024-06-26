package com.example.roombooking.dto;

import com.example.roombooking.utilities.BookingPriceCalculator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal totalPrice;

    public BookingDTO(CustomerLiteDTO customer, RoomLiteDTO room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = new BookingPriceCalculator()
                .calculateTotalPrice(startDate, endDate, room.getPrice(), numberOfPeople);
    }

}
