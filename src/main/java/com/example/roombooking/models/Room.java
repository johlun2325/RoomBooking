package com.example.roombooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long id;

//    @Enumerated(EnumType.STRING)

    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    private double price;
//    private int extraBeds;

    public Room(RoomType roomType, double price) {
        this.roomType = roomType;
        this.price = price;

    }

}
