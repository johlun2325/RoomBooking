package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.ContractCustomer;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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
                .contactName(contractCustomer.getContactName())
                .contactTitle(contractCustomer.getContactTitle())
                .streetAddress(contractCustomer.getStreetAddress())
                .city(contractCustomer.getCity())
                .postalCode(contractCustomer.getPostalCode())
                .country(contractCustomer.getCountry())
                .phone(contractCustomer.getPhone())
                .fax(contractCustomer.getFax())
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

    @Override
    public List<ContractCustomerDTO> findAllSorted(String sortOrder, String sortColumn) {
        return contractCustomerRepo.findAll(Sort.by(Sort.Direction.fromString(sortColumn), sortOrder))
                .stream()
                .map(this::convertToContractCustomerDto)
                .toList();
    }
}
