package com.example.roombooking.services;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.ContractCustomer;
import org.springframework.data.domain.Page;

public interface ContractCustomerService {

    ContractCustomerDTO findContractCustomerById(Long id);

    Page<ContractCustomerDTO> findAllContractCustomers(int pageNumber, int pageSize);

    ContractCustomer convertDtoToContractCustomer(ContractCustomerDTO contractCustomer);

    ContractCustomerDTO convertToContractCustomerDto(ContractCustomer contractCustomer);

    Page<ContractCustomerDTO> findAllSorted(String sortOrder, String sortColumn, int pageNumber, int pageSize);

    Page<ContractCustomerDTO> findAllByCompanyNameStartingWith(String query, String sortOrder, String sortColumn, int pageNumber, int pageSize);
}
