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
public class MiniBookingDTO {

    private Long id;
    private MiniRoomDTO room;
    private LocalDate startDate;
    private LocalDate endDate;

}
