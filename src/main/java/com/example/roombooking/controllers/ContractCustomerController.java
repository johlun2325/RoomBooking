package com.example.roombooking.controllers;


import com.example.roombooking.models.ContractCustomer;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contractCustomer")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping()
    List<ContractCustomer> getAllContractCustomers() {
        return contractCustomerService.findAllContractCustomers();
    }

    @GetMapping({"/{id}"})
    ContractCustomer getContractCustomer(@PathVariable Long id) {
        return contractCustomerService.findContractCustomerById(id);
    }

}
