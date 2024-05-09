package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BusinessCustomerDTO;
import com.example.roombooking.models.BusinessCustomer;
import com.example.roombooking.repos.BusinessCustomerRepo;
import com.example.roombooking.services.BusinessCustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BusinessCustomerImpl implements BusinessCustomerService {

    private final BusinessCustomerRepo businessCustomerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCustomerImpl.class);

    @Override
    public BusinessCustomerDTO convertToBusinessCustomerDto(BusinessCustomer businessCustomer) {
        return BusinessCustomerDTO.builder()
                .id(businessCustomer.getId())
                .companyName(businessCustomer.getCompanyName())
                .customerName(businessCustomer.getContactName())
                .country(businessCustomer.getCountry())
                .build();
    }

    @Override
    public BusinessCustomer convertDtoToBusinessCustomer(BusinessCustomerDTO businessCustomer) {
        return businessCustomerRepo.findById(businessCustomer.getId()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public BusinessCustomerDTO findContractCustomerById(Long id) {
        return businessCustomerRepo.findById(id)
                .map(this::convertToBusinessCustomerDto)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<BusinessCustomerDTO> findAllContractCustomers() {
        return businessCustomerRepo.findAll()
                .stream()
                .map(this::convertToBusinessCustomerDto)
                .toList();
    }
}
