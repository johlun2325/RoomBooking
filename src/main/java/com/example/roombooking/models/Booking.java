package com.example.roombooking.models;

import com.example.roombooking.utilities.BookingPriceCalculator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Min(value = 0, message = "Price cannot be lower than 0")
    private BigDecimal totalPrice;

    public Booking(Long id, Customer customer, Room room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = new BookingPriceCalculator()
                .calculateTotalPrice(startDate, endDate, room.getPrice(), numberOfPeople);
    }

    public Booking(Customer customer, Room room, int numberOfPeople, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.numberOfPeople = numberOfPeople;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = new BookingPriceCalculator()
                .calculateTotalPrice(startDate, endDate, room.getPrice(), numberOfPeople);
    }

    public void setDiscount(BigDecimal discount) {
        if (discount.compareTo(this.totalPrice) > 0) {
            discount = BigDecimal.ZERO;
        }

        var difference = this.totalPrice.subtract(discount);
        this.setTotalPrice(difference);
    }
}
