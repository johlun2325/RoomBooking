package com.example.roombooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Pattern(regexp = "^[A-Öa-ö ]+$", message = "Name can only contain Swedish letters and spaces")
    private String name;

    @Column(unique = true)
    @Pattern(regexp = "\\d{6}-\\d{4}",
             message = "Personal number must be in the format XXXXXX-XXXX")
    private String ssn;

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Pattern(regexp = "^[A-Za-z0-9.-_@\\s]+", message = "Email must contain only English letters, spaces, numbers, hyphen, dot, at sign and underscore.")
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

    public Customer(String name, String ssn, String email) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    //För att test ska funka
    public Customer(Long id, String name, String ssn, String email) {
        this.id = id;
        this.name = name;
        this.ssn = ssn;
        this.email = email;
    }

    public Customer(Long id, String name, String ssn, String email, List<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.bookings = bookings;
    }
}

