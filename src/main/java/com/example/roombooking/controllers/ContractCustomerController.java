package com.example.roombooking.controllers;


import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contractCustomer")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    @GetMapping("/all")
    public String getAllContractCustomers(Model model) {
        List<ContractCustomerDTO> all = contractCustomerService.findAllContractCustomers();
        model.addAttribute("allContractCustomers", all);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("contractCustomerId", "Företagskund-ID");
        model.addAttribute("companyName", "Företagsnamn");
        model.addAttribute("customerName", "Kundnamn");
        model.addAttribute("country", "Land");

        return "all-contract-customers";
    }

    @GetMapping({"/{id}"})
    String getContractCustomer(@PathVariable Long id, Model model) {
        ContractCustomerDTO businessCustomer = contractCustomerService.findContractCustomerById(id);
        model.addAttribute("contractCustomer", businessCustomer);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Företagskund");
        model.addAttribute("contractCustomerId", "Företagskund-ID");
        model.addAttribute("companyName", "Företagsnamn");
        model.addAttribute("customerName", "Kundnamn");
        model.addAttribute("country", "Land");

        return "show-contract-customer-info";
    }

}
