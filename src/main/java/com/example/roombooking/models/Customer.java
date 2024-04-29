package com.example.roombooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Pattern(regexp = "^[A-Öa-ö ]+$", message = "Name can only contain Swedish letters and spaces")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Social security number is mandatory")
    @Pattern(regexp = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-2]|3[0-1])-\\d{4}",
             message = "Personal number must be in the format YYMMDD-XXXX")
    private String ssn;

    @NotBlank(message = "Email is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    // TODO: Regex pattern for email
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

