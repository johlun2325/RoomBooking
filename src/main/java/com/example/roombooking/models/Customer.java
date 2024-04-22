package com.example.roombooking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String ssn;
    private String email;

    public Customer(String name, String ssn, String email) {
        this.name = name;
        this.ssn = ssn;
        this.email = email;
    }
}

