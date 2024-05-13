package com.example.roombooking.controllers;


import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public String getAllContractCustomers(Model model,
                                          @RequestParam(defaultValue = "1") int pageNumber,
                                          @RequestParam(defaultValue = "20") int pageSize) {

        var page = contractCustomerService.findAllContractCustomers(pageNumber, pageSize);


        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");
        model.addAttribute("placeholder", "Sök företag...");

        model.addAttribute("allContractCustomers", page);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages",  page.getTotalPages());

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
                       @RequestParam(defaultValue = "1") int pageNumber,
                       @RequestParam(defaultValue = "20") int pageSize,
                       @RequestParam(defaultValue = "companyName") String sortColumn,
                       @RequestParam(defaultValue = "ASC") String sortOrder,
                       @RequestParam(defaultValue = "") String query) {

        model.addAttribute("pageHeader", "Företagskunder");
        model.addAttribute("header", "Alla företagskunder");
        model.addAttribute("companyName", "Företag");
        model.addAttribute("contactName", "Namn");
        model.addAttribute("country", "Land");
        model.addAttribute("placeholder", "Sök företag...");


        model.addAttribute("query", query.trim());

        Page<ContractCustomerDTO> page;

        if (query.isEmpty()) {
            page = contractCustomerService.findAllSorted(sortOrder, sortColumn, pageNumber, pageSize);
        } else {
            page = contractCustomerService.findAllByCompanyNameStartingWith(query.trim(), sortOrder, sortColumn, pageNumber, pageSize);
        }
        model.addAttribute("allContractCustomers", page);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages",  page.getTotalPages());

        return "all-contract-customers";
    }
}
