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


    private final ContractCustomerRepo contractCustomerRepo;

    @GetMapping("/all")
    public String getAllContractCustomers(Model model) {
        model.addAttribute("allContractCustomers", contractCustomerService.findAllContractCustomers());
        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");

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
                       @RequestParam(defaultValue = "") String q) {

        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");


        var sort = Sort.by(Sort.Direction.fromString(sortOrder), sortColumn);
        if (!q.isEmpty()) {
            q = q.trim();
            model.addAttribute("q", q);
            model.addAttribute("allContractCustomers", contractCustomerRepo.findAllByCompanyNameStartingWith(q, sort));
        } else {
            model.addAttribute("q", "");
            model.addAttribute("allContractCustomers", contractCustomerService.findAllSorted(sortOrder, sortColumn));
        }

        return "all-contract-customers";
    }

}
