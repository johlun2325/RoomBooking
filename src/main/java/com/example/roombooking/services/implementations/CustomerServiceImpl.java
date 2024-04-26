package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.MiniBookingDTO;
import com.example.roombooking.dto.MiniCustomerDTO;
import com.example.roombooking.dto.MiniRoomDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Customer customerDTOtoCustomer(CustomerDTO customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public MiniCustomerDTO customerToMiniCustomerDTO(Customer customer) {
        return MiniCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public CustomerDTO customerToCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(booking -> new MiniBookingDTO(
                                booking.getId(),
                                new MiniRoomDTO(
                                        booking.getRoom().getId(),
                                        booking.getRoom().getPrice(),
                                        booking.getRoom().getRoomType()),
                                booking.getNumberOfPeople(),
                                booking.getStartDate(),
                                booking.getEndDate()))
                        .toList())
                .build();
    }

    @Override
    public List<CustomerDTO> getAllCustomersDTO() {
        return customerRepo.findAll()
                .stream()
                .map(this::customerToCustomerDTO)
                .toList();
    }

    @Override
    public CustomerDTO getCustomerDTO(Long id) {
        return customerRepo.findById(id)
                .map(this::customerToCustomerDTO)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String addCustomer(CustomerDTO newCustomer) {
        return null;
    }
}
