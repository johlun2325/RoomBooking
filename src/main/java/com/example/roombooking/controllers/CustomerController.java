package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Get all with thymeleaf
    @GetMapping("/all")
    public String getAllCustomers(Model model) {
        List<CustomerDTO> all = customerService.findAllCustomers();
        model.addAllAttributes(Map.of(
                "allCustomers", all,
                "header", "Alla kunder",
                "id", "Id",
                "name", "Namn",
                "delete", "Delete",
                "update", "Update",
                "hem", "Hem"));

        return "allCustomers";
    }

    @GetMapping({"/{id}"})
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    //thymeleaf update
    @RequestMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model) {
        CustomerDTO customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomerForm";
    }

    @PostMapping("/update")
    public String addCustomer(CustomerDTO customer){
        customerService.updateCustomer(customer);
        return "redirect:/customer/all";
    }

    // TODO: Add error message on the frontend for trying to remove customer with bookings
    // Delete with thymeleaf
    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return "redirect:/customer/all";
    }

}
