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
        List<ContractCustomerDTO> allContractCustomer = contractCustomerService.findAllContractCustomers();
        model.addAttribute("allContractCustomers", allContractCustomer);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");

        return "all-contract-customers";
    }

    @GetMapping({"/{id}"})
    String getContractCustomer(@PathVariable Long id, Model model) {
        ContractCustomerDTO contractCustomer = contractCustomerService.findContractCustomerById(id);
        model.addAttribute("contractCustomer", contractCustomer);
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Företagskund");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("contactTitle", "Titel");
        model.addAttribute("streetAddress", "Adress");
        model.addAttribute("city", "Stad");
        model.addAttribute("postalCode", "Postnummer");
        model.addAttribute("country", "Land");
        model.addAttribute("phone", "Mobilnummer");
        model.addAttribute("fax", "Fax");

        return "show-contract-customer-info";
    }

}
