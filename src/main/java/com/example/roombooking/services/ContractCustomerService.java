package com.example.roombooking.services;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.ContractCustomer;

import java.util.List;

public interface ContractCustomerService {

    ContractCustomerDTO findContractCustomerById(Long id);

    List<ContractCustomerDTO> findAllContractCustomers();

    ContractCustomer convertDtoToContractCustomer(ContractCustomerDTO contractCustomer);

    ContractCustomerDTO convertToContractCustomerDto(ContractCustomer contractCustomer);

}
