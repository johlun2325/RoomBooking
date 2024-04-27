package com.example.roombooking.controllers;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("all")
    public String getAllCustomers(Model model) {
        List<CustomerDTO> all = customerService.findAllCustomers();
        model.addAttribute("allCustomers", all);
        model.addAttribute("header", "Alla kunder");
        model.addAttribute("id", "Id");
        model.addAttribute("name", "Namn");
        return "allCustomers";
    }

    @GetMapping({"/{id}"})
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerDTO customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/update")
    public String updateCustomer(@RequestBody CustomerDTO customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestBody CustomerDTO customer) {
        return customerService.deleteCustomer(customer);
    }

    // HATEOAS: Not used
//    @GetMapping()
//    CollectionModel<EntityModel<CustomerDTO>> all() {
//        return customerService.all();
//    }

    // HATEOAS: Not used
//    @GetMapping("/{id}")
//    EntityModel<CustomerDTO> one(@PathVariable Long id) {
//        return customerService.one(id);
//    }

}
