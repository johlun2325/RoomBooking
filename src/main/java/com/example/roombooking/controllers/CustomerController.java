package com.example.roombooking.controllers;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

//    @GetMapping()
//    CollectionModel<EntityModel<Customer>> all() {
//
//        List<EntityModel<Customer>> customers = customerRepo.findAll().stream()
//                .map(customer -> EntityModel.of(customer,
//                        linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
//                        linkTo(methodOn(CustomerController.class).all()).withRel("customers")))
//                .toList();
//
//        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
//    }
//
//    @GetMapping("/{id}")
//    EntityModel<Customer> one(@PathVariable Long id) {
//
//        Customer customer = customerRepo.findById(id)
//                .orElseThrow(() -> {
//                    final String WARNING_MESSAGE = "No customer with ID: %s was found".formatted(id);
//                    logger.warn(WARNING_MESSAGE);
//                    return new NoSuchElementException(WARNING_MESSAGE);
//                });
//
//        return EntityModel.of(customer,
//                linkTo(methodOn(CustomerController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
//    }
//
//    @PostMapping("/add")
//    public CollectionModel<EntityModel<Customer>> addCustomer(@RequestBody NewCustomerRequest request) {
//
//        var newCustomer = customerRepo.findCustomerBySsn(request.getSsn());
//
//        if (newCustomer.isEmpty()) {
//            customerRepo.save(new Customer(request.getName(), request.getSsn(), request.getEmail()));
//            logger.info("New customer was added");
//            return all();
//        }
//
//        logger.warn("Customer is already present: ID {}", newCustomer.get().getId());
//        return all();
//    }
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
