package com.example.roombooking.controllers;


import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contractCustomer")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;


    @GetMapping("/all")
    public String getAllContractCustomers(Model model) {
        model.addAttribute("allContractCustomers", contractCustomerService.findAllContractCustomers());
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");
        model.addAttribute("placeholder", "Sök företag...");

        return "all-contract-customers";
    }

    @GetMapping({"/{id}"})
    public String getContractCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("contractCustomer", contractCustomerService.findContractCustomerById(id));
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

    @GetMapping("/all/sort")
    public String sort(Model model,
                       @RequestParam(defaultValue = "companyName") String sortColumn,
                       @RequestParam(defaultValue = "ASC") String sortOrder,
                       @RequestParam(defaultValue = "") String query) {

        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");
        model.addAttribute("placeholder", "Sök företag...");

        query = query.trim();

        if (query.isEmpty()) {
            model.addAttribute("allContractCustomers", contractCustomerService.findAllSorted(sortOrder, sortColumn));
        } else {
            model.addAttribute("query", query);
            model.addAttribute("allContractCustomers", contractCustomerService.findAllByCompanyNameStartingWith(query, sortOrder, sortColumn));
        }

        return "all-contract-customers";
    }
}
