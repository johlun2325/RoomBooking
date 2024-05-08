package com.example.roombooking.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
public class ContractCustomer {

        @Id
        @GeneratedValue
        @JacksonXmlProperty(localName = "id")
        public Long id;

        @JacksonXmlProperty(localName = "companyName")
        public String companyName;

        @JacksonXmlProperty(localName = "contactName")
        public String contactName;

        @JacksonXmlProperty(localName = "contactTitle")
        public String contactTitle;

        @JacksonXmlProperty(localName = "streetAddress")
        public String streetAddress;

        @JacksonXmlProperty(localName = "city")
        public String city;

        @JacksonXmlProperty(localName = "postalCode")
        public int postalCode;

        @JacksonXmlProperty(localName = "country")
        public String country;

        @JacksonXmlProperty(localName = "phone")
        public String phone;

        @JacksonXmlProperty(localName = "fax")
        public String fax;

}
