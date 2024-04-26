package com.example.roombooking.dto;


import com.example.roombooking.models.Booking;
import com.example.roombooking.models.RoomType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {

    private Long id;
    private double price;
    private RoomType roomType;

    private List<?> bookings;
}
