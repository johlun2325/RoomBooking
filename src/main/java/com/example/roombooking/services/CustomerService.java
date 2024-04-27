package com.example.roombooking.services;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.models.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface CustomerService {

    Customer convertDtoToCustomer(CustomerDTO customer);

    CustomerLiteDTO convertToCustomerLiteDto(Customer customer);

    CustomerDTO convertToCustomerDto(Customer customer);

    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomerById(Long id);

    // HATEOAS: Not used
    CollectionModel<EntityModel<CustomerDTO>> all();

    // HATEOAS: Not used
    EntityModel<CustomerDTO> one(Long id);

    String addCustomer(CustomerDTO customer);

    String deleteCustomer(CustomerDTO customer);

    String updateCustomer(CustomerDTO customer);

}
