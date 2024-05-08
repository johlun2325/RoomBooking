package com.example.roombooking.controllers;


import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BusinessCustomerDTO;
import com.example.roombooking.models.BusinessCustomer;
import com.example.roombooking.services.BusinessCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/businessCustomer")
@RequiredArgsConstructor
public class BusinessCustomerController {

    private final BusinessCustomerService businessCustomerService;

    @GetMapping()
    public String getAllBusinessCustomers(Model model) {
        List<BusinessCustomerDTO> all = businessCustomerService.findAllContractCustomers();
        model.addAttribute("allBusinessCustomers", all);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("businessCustomerId", "Företagskund-ID");
        model.addAttribute("companyName", "Företagsnamn");
        model.addAttribute("customerName", "Kundnamn");
        model.addAttribute("country", "Land");

        return "all-business-customers";
    }

    @GetMapping({"/{id}"})
    String getBusinessCustomer(@PathVariable Long id, Model model) {
        BusinessCustomerDTO businessCustomer = businessCustomerService.findContractCustomerById(id);
        model.addAttribute("businessCustomers", businessCustomer);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Företagskund");
        model.addAttribute("businessCustomerId", "Företagskund-ID");
        model.addAttribute("companyName", "Företagsnamn");
        model.addAttribute("customerName", "Kundnamn");
        model.addAttribute("country", "Land");

        return "show-business-customer-info";
    }

}
