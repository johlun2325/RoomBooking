package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerLiteDTO {

    private Long id;
    private String name;
    private String ssn;
    private String email;

    public CustomerLiteDTO(String ssn) {
        this.ssn = ssn;
    }
}
