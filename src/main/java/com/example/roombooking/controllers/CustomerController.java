package com.example.roombooking.controllers;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("all")
    List<CustomerDTO> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping({"/{id}"})
    CustomerDTO getCustomer(@PathVariable Long id) {
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
