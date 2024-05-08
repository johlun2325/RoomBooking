package com.example.roombooking.services;

import com.example.roombooking.dto.BusinessCustomerDTO;
import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.models.BusinessCustomer;
import com.example.roombooking.models.Customer;

import java.util.List;

public interface BusinessCustomerService {

    BusinessCustomerDTO findContractCustomerById(Long id);

    List<BusinessCustomerDTO> findAllContractCustomers();

    BusinessCustomer convertDtoToBusinessCustomer(BusinessCustomerDTO businessCustomer);

    BusinessCustomerDTO convertToBusinessCustomerDto(BusinessCustomer businessCustomer);

}
