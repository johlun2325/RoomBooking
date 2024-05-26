package com.example.roombooking.controllers;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        List<CustomerDTO> all = customerService.findAllCustomers();
        model.addAttribute("allCustomers", all);
        model.addAttribute("pageHeader", "Kunder");
        model.addAttribute("header", "Alla kunder");
        model.addAttribute("idTh", "ID");
        model.addAttribute("nameTh", "Fullständigt namn");
        model.addAttribute("ssnTh", "Personnummer");
        model.addAttribute("emailTh", "E-postadress");
        model.addAttribute("delete", "Ta bort");
        model.addAttribute("update", "Uppdatera");

        return "customer/customers.html";
    }

    @GetMapping({"/{id}"})
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/new")
    public String openNewCustomerPage(Model model) {
        model.addAllAttributes(Map.of(
                "pageTitle", "Ny Kund",
                "header","Lägg till ny kund",
                "fullNameText", "Fullständigt namn",
                "nameTitle", "Please enter only Swedish letters and spaces.",
                "ssnText", "Personnummer",
                "ssnTitle", "Please enter a number in the format YYMMDD-XXXX.",
                "emailText", "E-postadress",
                "buttonText", "Lägg till"));

        return "customer/new-customer.html";
    }

    @PostMapping("/add")
    public String addCustomer(@RequestParam String name,
                              @RequestParam String ssn,
                              @RequestParam String email) {

        customerService.addCustomer(new CustomerDTO(name, ssn, email));
        return "redirect:/customer/all";
    }

    @GetMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model) {
        CustomerDTO customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("pageTitle", "Kund");
        model.addAttribute("header", "Uppdatera kund");
        model.addAttribute("nameText", "Ändra fullständigt namn");
        model.addAttribute("ssnText", "Ändra personnummer");
        model.addAttribute("emailText", "Ändra e-postadress");
        model.addAttribute("buttonText", "Uppdatera");

        return "customer/update-customer.html";
    }

    @PostMapping("/update")
    public String addCustomer(CustomerDTO customer){
        customerService.updateCustomer(customer);
        return "redirect:/customer/all";
    }

    // TODO: Add error message on the frontend for trying to remove customer with bookings
    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return "redirect:/customer/all";
    }
}
