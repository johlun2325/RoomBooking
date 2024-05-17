package com.example.roombooking.models.External;

import com.example.roombooking.models.External.ContractCustomer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "allcustomers")
public class ContractCustomers {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "customers")
    private List<ContractCustomer> contractCustomers;

}
