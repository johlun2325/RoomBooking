package com.example.roombooking.dto;

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
public class BookingLiteDTO {

    private Long id;
    private RoomLiteDTO room;
    private int numberOfPeople;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;

}
