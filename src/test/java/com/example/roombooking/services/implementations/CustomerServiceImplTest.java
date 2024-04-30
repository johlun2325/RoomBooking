package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repo;

//    @Override
//    public Customer convertDtoToCustomer(CustomerDTO customer) {
//        return Customer.builder()
//                .id(customer.getId())
//                .name(customer.getName())
//                .ssn(customer.getSsn())
//                .email(customer.getEmail())
//                .build();
//    }

    private Long id = 1L;
    private String name = "Anna";
    private String ssn = "343434-3434";
    private String email = "mockmock@mail.se";

    private Customer customer = new Customer(id,name,ssn,email);

    @Test
    void convertDtoToCustomer() {
    }

    @Test
    void convertToCustomerLiteDto() {
    }

    @Test
    void convertToCustomerDto() {


    }

    @Test
    void convertLiteDtoToCustomer() {
    }

    @Test
    void findAllCustomers() {
    }

    @Test
    void findCustomerById() {
    }

    @Test
    void addCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomerById() {
    }
}