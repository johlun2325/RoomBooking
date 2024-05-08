package com.example.roombooking.services;

import com.example.roombooking.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {

    ContractCustomer findContractCustomerById(Long id);

    List<ContractCustomer> findAllContractCustomers();

}
