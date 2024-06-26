package com.example.roombooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Min(value = 1, message = "Price must be at least 1")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room(double price, RoomType roomType) {
        this.price = BigDecimal.valueOf(price);
        this.roomType = roomType;
        this.bookings = new ArrayList<>();
    }
}
