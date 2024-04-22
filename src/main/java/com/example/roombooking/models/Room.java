package com.example.roombooking.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Enumerated(EnumType.STRING)
    private Type type;
    private double price;
    private int extraBeds;

    @Transient
    private int extraBedCapacity;
    @Transient
    private final double EXTRA_BED_PRICE = 499.99;

    public Room(Type type, double price) {
        this(type, price, 0);
    }

    public Room(Type type, double price, int extraBeds) {
        this.type = type;
        this.price = price;
        this.extraBeds = extraBeds;

        switch (type) {
            case SINGLE -> extraBedCapacity = 0;
            case DOUBLE -> extraBedCapacity = 1;
            case LARGE_DOUBLE -> extraBedCapacity = 2;
        }

        if (extraBeds > extraBedCapacity)
            throw new IllegalArgumentException("Extra beds exceeds bed capacity");
        else
            this.price += (extraBeds * EXTRA_BED_PRICE);
    }

    public void setExtraBeds(int extraBeds) {
        if (extraBeds > extraBedCapacity)
            throw new IllegalArgumentException("Extra beds exceeds bed capacity");
        else {
            this.extraBeds = extraBeds;
            this.price += (extraBeds * EXTRA_BED_PRICE);
        }
    }
}
