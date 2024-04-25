package com.example.roombooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private double price;

    @ManyToOne
    @JoinColumn
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room(double price, RoomType roomType) {
        this.price = price;
        this.roomType = roomType;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        boolean notFound = bookings.stream().noneMatch(it -> it.equals(booking));
        if (notFound) {
            bookings.add(booking);
        }
    }

    public void removeBooking(Booking booking) {
        bookings.removeIf(it -> it.equals(booking));
    }

}
