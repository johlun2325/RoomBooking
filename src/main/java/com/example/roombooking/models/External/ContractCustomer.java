package com.example.roombooking.models.External;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
        private String companyName;

        @JacksonXmlProperty(localName = "contactName")
        private String contactName;

        @JacksonXmlProperty(localName = "contactTitle")
        private String contactTitle;

        @JacksonXmlProperty(localName = "streetAddress")
        private String streetAddress;

        @JacksonXmlProperty(localName = "city")
        private String city;

        @JacksonXmlProperty(localName = "postalCode")
        private int postalCode;

        @JacksonXmlProperty(localName = "country")
        private String country;

        @JacksonXmlProperty(localName = "phone")
        private String phone;

        @JacksonXmlProperty(localName = "fax")
        private String fax;
}
