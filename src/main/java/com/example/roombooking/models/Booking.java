package com.example.roombooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    @ManyToOne
    @JoinColumn
    private Room room;

    @Min(value = 1, message = "Amount of people must be at least 1")
    @Max(value = 6, message = "Amount of people must be no more than 6")
    private int numberOfPeople;

    private LocalDate startDate;
    private LocalDate endDate;

    public Booking(Customer customer, Room room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}


// @NotBlank krockar med UpdateForm sidan, då formen enbart skickar numberOfPeople