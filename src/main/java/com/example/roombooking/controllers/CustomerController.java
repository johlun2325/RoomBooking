package com.example.roombooking.controllers;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @GetMapping()
    List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomersDTO();
    }

    @GetMapping({"/{id}"})
    CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.getCustomerDTO(id);
    }

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerDTO newCustomer) {

        return null;
    }

//    @GetMapping()
//    CollectionModel<EntityModel<CustomerDTO>> all() {
//
//        List<EntityModel<CustomerDTO>> customers = customerService.getAllCustomersDTO().stream()
//                .map(customer -> EntityModel.of(customer,
//                        linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
//                        linkTo(methodOn(CustomerController.class).all()).withRel("customers")))
//                .toList();
//
//        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
//    }
//
//    @GetMapping("/{id}")
//    EntityModel<CustomerDTO> one(@PathVariable Long id) {
//
//        CustomerDTO customer = customerService.getCustomerDTO(id);
//
//        return EntityModel.of(customer,
//                linkTo(methodOn(CustomerController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
//    }
//
//
//    @PutMapping("/updateEmail")
//    public CollectionModel<EntityModel<Customer>> updateCustomer(@RequestBody CustomerUpdateEmailRequest request) {
//        return customerRepo.findById(request.getId())
//                .map(customer -> {
//                    customer.setEmail(request.getEmail());
//                    customerRepo.save(customer);
//                    logger.info("Customer with ID: {} updated", customer.getId());
//                    return all();
//                })
//                .orElseGet(() -> {
//                    logger.warn("Customer with ID: {} not found", request.getId());
//                    return all();
//                });
//    }
//
//    @DeleteMapping("/delete")
//    public CollectionModel<EntityModel<Customer>> deleteCustomer(@RequestBody DeleteCustomerRequest request) {
//        var customerOptional = customerRepo.findCustomerBySsn(request.getSsn());
//
//        if (customerOptional.isEmpty()) {
//            logger.warn("Customer with SSN: {} not found", request.getSsn());
//            return all();
//        }
//
//        Customer customer = customerOptional.get();
//        boolean hasBookings = bookingRepo.existsByCustomer(customer);
//        if (hasBookings) {
//            logger.warn("Customer with SSN: {} has booking history and thereby not deleted", customer.getSsn());
//            return all();
//        }
//
//        customerRepo.delete(customer);
//        logger.info("Customer with SNN: {} deleted", customer.getSsn());
//        return all();
//    }

}
