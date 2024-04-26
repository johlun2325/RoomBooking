package com.example.roombooking.services;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.MiniCustomerDTO;
import com.example.roombooking.models.Customer;

import java.util.List;

public interface CustomerService {

    Customer customerDTOtoCustomer(CustomerDTO customer);

    MiniCustomerDTO customerToMiniCustomerDTO(Customer customer);

    CustomerDTO customerToCustomerDTO(Customer customer);

    List<CustomerDTO> getAllCustomersDTO();

    CustomerDTO getCustomerDTO(Long id);

    String addCustomer(CustomerDTO customerDTO);
}
