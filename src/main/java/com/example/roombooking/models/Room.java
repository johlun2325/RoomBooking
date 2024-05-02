package com.example.roombooking.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
