package com.example.roombooking.dto;

import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private MiniCustomerDTO customer;
    private MiniRoomDTO room;
    private LocalDate startDate;
    private LocalDate endDate;
}
