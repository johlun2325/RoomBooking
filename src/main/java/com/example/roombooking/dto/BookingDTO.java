package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
