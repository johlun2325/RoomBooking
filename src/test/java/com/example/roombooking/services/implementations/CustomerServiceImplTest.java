package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repo;

    private CustomerServiceImpl service = new CustomerServiceImpl(repo);


    private Long id = 1L;
    private String name = "Anna";
    private String ssn = "343434-3434";
    private String email = "mockmock@mail.se";

    private Customer customer = new Customer(id,name,ssn,email, new ArrayList<>());

    @Test
    void convertDtoToCustomer() {
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

    }


//    @Test
//    void getAllKonto() {
//        when(kontoRepo.findAll()).thenReturn(Arrays.asList(konto));
//        KontoServiceImpl service2 = new KontoServiceImp(kontoRepo, kundRepo);
//        List<DetailedKontoDto> allKontos = service2.getAllKonto();
//
//        assertTrue(allKontos.size() == 1);
//    }
    @Test
    void findAllCustomers() {
        when(repo.findAll()).thenReturn(Arrays.asList(customer));
        CustomerServiceImpl serv = new CustomerServiceImpl(repo);

        List<CustomerDTO> allCustomers = serv.findAllCustomers();
        assertTrue(allCustomers.size() == 1);
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