package com.example.roombooking.services;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer convertDtoToCustomer(CustomerDTO customer);

    CustomerLiteDTO convertToCustomerLiteDto(Customer customer);

    CustomerDTO convertToCustomerDto(Customer customer);

    Customer convertLiteDtoToCustomer(CustomerLiteDTO customer);

    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomerById(Long id);

    void addCustomer(CustomerDTO customer);

    void deleteCustomerById(Long id);

    String updateCustomer(CustomerDTO customer);

}
