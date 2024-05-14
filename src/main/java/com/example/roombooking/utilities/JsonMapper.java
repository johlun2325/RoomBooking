package com.example.roombooking.utilities;

import com.example.roombooking.models.BlacklistedCustomer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class JsonMapper {

    private List<BlacklistedCustomer> blacklistedCustomers;

    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

//        BlacklistedCustomer customer = mapper.readValue(new URL("https://jsonplaceholder.typicode.com/posts/1"), BlacklistedCustomer.class);

        blacklistedCustomers = mapper.readValue(new URL("https://jsonplaceholder.typicode.com/posts"), new TypeReference<>() {});
    }


}
