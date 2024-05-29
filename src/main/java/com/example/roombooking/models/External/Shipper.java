package com.example.roombooking.models.External;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipper {

    @Id
    @GeneratedValue
    private Long internalId;

    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9.-_@\\s]+", message = "Email must contain only English letters, spaces, numbers, hyphen, dot, at sign and underscore.")
    private String email;

    @Pattern(regexp = "^[A-Öa-ö0-9.,-_\\s]+", message = "Contact name must contain only swedish letters, spaces, numbers, hyphen, dot, comma and underscore.")
    private String companyName;

    @Pattern(regexp = "^[A-Öa-ö'\\s]+", message = "Contact name must contain only swedish letters, apostrophe and spaces.")
    private String contactName;

    @Pattern(regexp = "^[A-Öa-ö-\\s]+", message = "Contact title must contain only swedish letters, spaces and hyphen.")
    private String contactTitle;

    @Pattern(regexp = "^[A-Öa-ö0-9\\s]+", message = "Street address must contain only swedish letters, numbers, and spaces.")
    private String streetAddress;

    @Pattern(regexp = "^[A-Öa-ö\\s]+", message = "City must contain only swedish letters and spaces.")
    private String city;

    @Pattern(regexp = "^[0-9\\s-]+$", message = "Postal code must only contain digits, space and hyphen.")
    private String postalCode;

    @Pattern(regexp = "^[A-Öa-ö\\s]+", message = "Country must contain only swedish letters and spaces.")
    private String country;

    @Pattern(regexp = "^[0-9\\s+()-]+$", message = "Phone number must only contain digits, space and hyphen, plus sign and parenthesis.")
    private String phone;

    @Pattern(regexp = "^[0-9\\s-]+$", message = "Fax must only contain digits, space and hyphen.")
    private String fax;
}
