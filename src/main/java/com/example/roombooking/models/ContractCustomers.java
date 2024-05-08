package com.example.roombooking.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@JacksonXmlRootElement(localName = "allcustomers")
@Data
public class ContractCustomers {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "customers")
    private List<ContractCustomer> contractCustomers;

}
