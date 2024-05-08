package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessCustomerDTO {

    private Long id;
    private String companyName;
    private String customerName;
    private String country;

}
