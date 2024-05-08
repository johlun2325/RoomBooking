package com.example.roombooking.controllers;


import com.example.roombooking.dto.BusinessCustomerDTO;
import com.example.roombooking.models.BusinessCustomer;
import com.example.roombooking.services.BusinessCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/businessCustomer")
@RequiredArgsConstructor
public class BusinessCustomerController {

    private final BusinessCustomerService businessCustomerService;

    @GetMapping()
    List<BusinessCustomerDTO> getAllContractCustomers() {
        return businessCustomerService.findAllContractCustomers();
    }

    @GetMapping({"/{id}"})
    BusinessCustomerDTO getContractCustomer(@PathVariable Long id) {
        return businessCustomerService.findContractCustomerById(id);
    }

}
