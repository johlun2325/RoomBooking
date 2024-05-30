package com.example.roombooking.models.External;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JacksonXmlRootElement(localName = "customers")
public class ContractCustomer {

        @Id
        @GeneratedValue
        private Long internalId;

        @JacksonXmlProperty(localName = "id")
        private Long id;

        @JacksonXmlProperty(localName = "companyName")
        @Pattern(regexp = "^[A-Öa-ö0-9.,-_\\s]+", message = "Contact name must contain only swedish letters spaces, numbers, hyphen, dot, comma and underscore.")
        private String companyName;

        @JacksonXmlProperty(localName = "contactName")
        @Pattern(regexp = "^[A-Öa-ö\\s]+", message = "Contact name must contain only swedish letters and spaces.")
        private String contactName;

        @JacksonXmlProperty(localName = "contactTitle")
        @Pattern(regexp = "^[A-Öa-ö-\\s]+", message = "Contact title must contain only swedish letters spaces and hyphen.")
        private String contactTitle;

        @JacksonXmlProperty(localName = "streetAddress")
        @Pattern(regexp = "^[A-Öa-ö0-9\\s]+", message = "Street address must contain only swedish letters, numbers, and spaces.")
        private String streetAddress;

        @JacksonXmlProperty(localName = "city")
        @Pattern(regexp = "^[A-Öa-ö\\s]+", message = "City must contain only swedish letters and spaces.")
        private String city;

        @JacksonXmlProperty(localName = "postalCode")
        private int postalCode;

        @JacksonXmlProperty(localName = "country")
        @Pattern(regexp = "^[A-Öa-ö\\s]+", message = "Country must contain only swedish letters and spaces.")
        private String country;

        @JacksonXmlProperty(localName = "phone")
        @Pattern(regexp = "^[0-9\\s+()-]+$", message = "Phone number must only contain digits, space and hyphen, plus sign and parenthesis.")
        private String phone;

        @JacksonXmlProperty(localName = "fax")
        @Pattern(regexp = "^[0-9\\s-]+$", message = "Fax must only contain digits, space and hyphen.")
        private String fax;
}
