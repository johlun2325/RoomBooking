package com.example.roombooking.services;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.ContractCustomer;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ContractCustomerService {

    ContractCustomerDTO findContractCustomerById(Long id);

    List<ContractCustomerDTO> findAllContractCustomers();

    ContractCustomer convertDtoToContractCustomer(ContractCustomerDTO contractCustomer);

    ContractCustomerDTO convertToContractCustomerDto(ContractCustomer contractCustomer);

    List<ContractCustomerDTO> findAllSorted(String sortOrder, String sortColumn);

    List<ContractCustomerDTO> findAllByCompanyNameStartingWith(String query, String sortOrder, String sortColumn);
    List<ContractCustomer> fetchContractCustomers();
}
