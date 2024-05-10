package com.example.roombooking.controllers;


import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/contractCustomer")
@RequiredArgsConstructor
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    // Ska egen inte vara här (vi måste använda DTO)
    private final ContractCustomerRepo contractCustomerRepo;

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
    public String getContractCustomer(@PathVariable Long id, Model model) {
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

    @GetMapping({})
    public String sort(Model model,
                       @RequestParam(defaultValue = "1") int pageNo,
                       @RequestParam(defaultValue = "ID") int pageSize,
                       @RequestParam(defaultValue = "name") String sortCol,
                       @RequestParam(defaultValue = "ASC") String sortOrder,
                       @RequestParam(defaultValue = "") String  q) {

        q = q.trim();

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortCol);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (!q.isEmpty()) {
            model.addAttribute("contractCustomers", contractCustomerRepo.findAllByContactNameContaining(q, sort));
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageNo", 1);
        } else {
            var list = contractCustomerRepo.findAllBy(sort);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("contractCustomers", list);
        }

        return "redirect:how-contract-customer-info";
    }

}
