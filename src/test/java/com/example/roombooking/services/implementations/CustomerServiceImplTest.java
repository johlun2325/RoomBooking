package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repo;

    @InjectMocks
    private CustomerServiceImpl service = new CustomerServiceImpl(repo);


    private Long id = 1L;
    private String name = "Anna";
    private String ssn = "343434-3434";
    private String email = "mockmock@mail.se";

    private Customer customer = new Customer(id,name,ssn,email, new ArrayList<>());

    private CustomerDTO customerDTO = new CustomerDTO().builder()
            .id(customer.getId()).name(customer.getName())
            .ssn(customer.getSsn()).email(customer.getEmail())
            .bookings(new ArrayList<>()).build();

    private CustomerLiteDTO customerLiteDTO = new CustomerLiteDTO().builder()
            .id(customer.getId()).name(customer.getName())
            .ssn(customer.getSsn()).email(customer.getEmail()).build();

    @Test
    void convertDtoToCustomer() {
        Customer actual = service.convertDtoToCustomer(customerDTO);
        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getEmail(), customer.getEmail());

        assertEquals(actual.getId(), customerDTO.getId());
    }

    @Test
    void convertToCustomerLiteDto() {
        CustomerLiteDTO actual = service.convertToCustomerLiteDto(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    void convertToCustomerDto() {
        CustomerDTO actual = service.convertToCustomerDto(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    void convertLiteDtoToCustomer() {
        Customer actual = service.convertLiteDtoToCustomer(customerLiteDTO);
        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getSsn(), customer.getSsn());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    void findAllCustomers() {
        when(repo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl serv = new CustomerServiceImpl(repo);

        List<CustomerDTO> allCustomers = serv.findAllCustomers();
        assertTrue(allCustomers.size() == 1);
    }

    @Test
    void findCustomerById() {
        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerServiceImpl serv = new CustomerServiceImpl(repo);
        CustomerDTO foundCustomer = serv.findCustomerById(customer.getId());

        assertEquals(customer.getId(), foundCustomer.getId());
        assertEquals(customer.getName(), foundCustomer.getName());
        assertEquals(customer.getSsn(), foundCustomer.getSsn());
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
    }

    @Test
    void addCustomer() {
        CustomerServiceImpl serv = new CustomerServiceImpl(repo);
        when(repo.save(any(Customer.class))).thenReturn(customer);

        serv.addCustomer(customerDTO);
        verify(repo, times(1)).save(any(Customer.class));

    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomerById() {
        when(repo.findById(customer.getId())).thenReturn(Optional.of(customer));
        CustomerServiceImpl serv = new CustomerServiceImpl(repo);
        serv.deleteCustomerById(customer.getId());
        verify(repo, times(1)).findById(id);
//        verify(repo, times(1)).deleteById(id);

    }

}