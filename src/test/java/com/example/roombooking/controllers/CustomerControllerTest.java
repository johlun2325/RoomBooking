package com.example.roombooking.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(customerController).isNotNull();
    }
    @Test
    void getAllCustomers() {
    }

    @Test
    void getCustomer() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateByForm() {
    }

    @Test
    void testAddCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}