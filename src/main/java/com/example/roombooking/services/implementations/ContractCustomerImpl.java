package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.ContractCustomer;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContractCustomerImpl implements ContractCustomerService {

    private final ContractCustomerRepo contractCustomerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    @Override
    public ContractCustomerDTO convertToContractCustomerDto(ContractCustomer contractCustomer) {
        return ContractCustomerDTO.builder()
                .id(contractCustomer.getInternalId())
                .companyName(contractCustomer.getCompanyName())
                .customerName(contractCustomer.getContactName())
                .country(contractCustomer.getCountry())
                .build();
    }

    @Override
    public ContractCustomer convertDtoToContractCustomer(ContractCustomerDTO contractCustomer) {
        return contractCustomerRepo.findById(contractCustomer.getId()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public ContractCustomerDTO findContractCustomerById(Long id) {
        return contractCustomerRepo.findById(id)
                .map(this::convertToContractCustomerDto)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ContractCustomerDTO> findAllContractCustomers() {
        return contractCustomerRepo.findAll()
                .stream()
                .map(this::convertToContractCustomerDto)
                .toList();
    }
}
