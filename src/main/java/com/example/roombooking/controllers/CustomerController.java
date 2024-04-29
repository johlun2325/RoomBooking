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
import java.util.function.ToDoubleBiFunction;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    //get all with thymeleaf
    @GetMapping("all")
    public String getAllCustomers(Model model) {
        List<CustomerDTO> all = customerService.findAllCustomers();
        model.addAttribute("allCustomers", all);
        model.addAttribute("header", "Alla kunder");
        model.addAttribute("id", "Id");
        model.addAttribute("name", "Namn");
        model.addAttribute("delete", "Delete");
        model.addAttribute("update", "Update");
        model.addAttribute("hem", "Hem");
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

//    @PutMapping("/update")
//    public String updateCustomer(@RequestBody CustomerDTO customer) {
//        return customerService.updateCustomer(customer);
//    }



    //thymeleaf update
    @RequestMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model) {
        CustomerDTO c = customerService.findCustomerById(id);
        model.addAttribute("customer", c);
        return "updateCustomerForm";
    }

    @PostMapping("/update")
    public String addCustomer(Model model, CustomerDTO c){
        customerService.addCustomer(c);
        return "redirect:/customer/all";
    }


    //delete with thymeleaf - lägg till kolla om bokning finns, då ej ta bort. I service ist?
    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        CustomerDTO c = customerService.findCustomerById(id);
        List<BookingLiteDTO> b =  c.getBookings();
        customerService.deleteCustomerById(id);
        return "redirect:/customer/all";
    }
//    @DeleteMapping("/delete")
//    public String deleteCustomer(@RequestBody CustomerDTO customer) {
//        return customerService.deleteCustomer(customer);
//    }

}
