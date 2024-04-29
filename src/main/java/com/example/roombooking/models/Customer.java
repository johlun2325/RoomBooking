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
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(unique = true) //kvar?
    private String ssn;
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    public Customer(String name, String ssn, String email) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
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

