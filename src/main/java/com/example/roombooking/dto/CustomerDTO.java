package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String name;
    private String ssn;
    private String email;
    private List<BookingLiteDTO> bookings;

    public CustomerDTO(String name, String ssn, String email) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
        this.bookings = new ArrayList<>();
    }
}
