package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.dto.ContractCustomerDTO;
import com.example.roombooking.models.External.ContractCustomer;
import com.example.roombooking.models.External.ContractCustomers;
import com.example.roombooking.repos.ContractCustomerRepo;
import com.example.roombooking.services.ContractCustomerService;
import com.example.roombooking.utilities.StreamProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContractCustomerImpl implements ContractCustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    @Getter
    private final StreamProvider streamProvider;
    private final ContractCustomerRepo contractCustomerRepo;
    private final IntegrationProperties integrationProperties;

    @Autowired
    public ContractCustomerImpl(StreamProvider streamProvider, ContractCustomerRepo contractCustomerRepo, IntegrationProperties integrationProperties) {
        this.streamProvider = streamProvider;
        this.contractCustomerRepo = contractCustomerRepo;
        this.integrationProperties = integrationProperties;
    }

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
        return contractCustomerRepo.findAll(Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
                .stream()
                .map(this::convertToContractCustomerDto)
                .toList();
    }

    @Override
    public List<ContractCustomerDTO> findAllByCompanyNameStartingWith(String query, String sortOrder, String sortColumn) {
        return contractCustomerRepo.findAllByCompanyNameStartingWith(query, Sort.by(Sort.Direction.fromString(sortOrder), sortColumn))
                .stream()
                .map(this::convertToContractCustomerDto)
                .toList();
    }

    @Override
    public List<ContractCustomer> fetchContractCustomers() {
        LOGGER.info("Starting to fetch contract customers from external service.");
        String url = integrationProperties.getContractCustomer().getUrl();

        var xmlModule = new JacksonXmlModule();
        xmlModule.setDefaultUseWrapper(false);
        ObjectMapper xmlMapper = new XmlMapper(xmlModule);

        try {
            InputStream stream = streamProvider.getDataStream(url);
            ContractCustomers contractCustomers = xmlMapper.readValue(stream, ContractCustomers.class);
            LOGGER.info("Fetched {} contract customers.", contractCustomers.getContractCustomers().size());

            return contractCustomers.getContractCustomers();

        } catch (IOException e) {
            LOGGER.error("An unexpected error occurred when fetching or saving contract customers", e);
            return Collections.emptyList();
        }
    }
}
