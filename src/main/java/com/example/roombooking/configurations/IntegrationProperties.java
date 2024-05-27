package com.example.roombooking.configurations;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "integrations")
@Data
public class IntegrationProperties {

    private BlacklistProperties blacklist;
    private ShipperProperties shipper;
    private ContractCustomerProperties contractCustomer;
    private EventProperties event;
    private SeedProperties seed;

}
