package com.example.roombooking.services.implementations;

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
    public ContractCustomer findContractCustomerById(Long id) {
        return contractCustomerRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ContractCustomer> findAllContractCustomers() {
        return contractCustomerRepo.findAll();
    }
}
