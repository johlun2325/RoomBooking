package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Customer;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    //customerDTO till customer
    @Override
    public Customer convertDtoToCustomer(CustomerDTO customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    //customer till customerLiteDTO
    @Override
    public CustomerLiteDTO convertToCustomerLiteDto(Customer customer) {
        return CustomerLiteDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    //customer till customerDTO
    @Override
    public CustomerDTO convertToCustomerDto(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(booking -> new BookingLiteDTO(
                                booking.getId(),
                                new RoomLiteDTO(
                                        booking.getRoom().getId(),
                                        booking.getRoom().getPrice(),
                                        booking.getRoom().getRoomType()),
                                booking.getNumberOfPeople(),
                                booking.getStartDate(),
                                booking.getEndDate()))
                        .toList())
                .build();
    }

    // Usage: When a new customer creates a new booking!
    @Override
    public Customer convertLiteDtoToCustomer(CustomerLiteDTO customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    // Find all customers - lista
    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(this::convertToCustomerDto)
                .peek(customer -> LOGGER.info("Customer data listed: ID {}", customer.getId()))
                .toList();
    }

    // TODO: No LOGGER here
    // Find 1 customer by id - obj
    @Override
    public CustomerDTO findCustomerById(Long id) {
        return customerRepo.findById(id)
                .map(this::convertToCustomerDto)
                .orElseThrow(NoSuchElementException::new);
    }


    @Override
    public void addCustomer(CustomerDTO customer) {
        customerRepo.findCustomerBySsn(customer.getSsn())
                .ifPresentOrElse(foundCustomer -> LOGGER.warn("Customer with SSN: {} exists", customer.getSsn()),
                        () -> {
                    customerRepo.save(convertDtoToCustomer(customer));
                    LOGGER.info("Customer with SSN: {} added", customer.getSsn());
                });
    }

//    @Override
//    public void addCustomer(CustomerDTO customer) {
//        Customer c = convertDtoToCustomer(customer);
//        Long id = c.getId();
//        Long idIsPresent = findAllCustomers().stream()
//                .filter(cust -> cust.getId() == id).map(cu -> cu.getId())
//                .findFirst()
//                .orElse(-1L);
//
//        if (idIsPresent == -1L){
//            customerRepo.save(c);
//            LOGGER.info("Customer with ID: {} added", c.getId());
//        }
//        else {
//            LOGGER.warn("Customer with ID: {} exists", c.getId());
//        }
//    }

//    @Override
//    public String updateCustomer(CustomerDTO customer) {
//        Customer c = convertDtoToCustomer(customer);
//
//        Long id = c.getId();
//        Long idIsPresent = findAllCustomers().stream().filter(cust -> cust.getId() == id).map(cu -> cu.getId()).findFirst().orElse(-1L);
//
//        if (idIsPresent == -1L){
//            customerRepo.save(c);
//            LOGGER.info("Customer with ID: {} does not exist, customer added", customer.getId());
//            return "Customer did not exist, customer added";
//        }
//        customerRepo.save(c);
//        LOGGER.info("Customer with ID: {} updated", customer.getId());
//        return "Customer updated";
//    }

    @Override
    public String updateCustomer(CustomerDTO customer) {
        var message = new AtomicReference<String>();

        return customerRepo.findById(customer.getId())
                .map(foundCustomer -> {
                    foundCustomer.setName(customer.getName());
                    foundCustomer.setSsn(customer.getSsn());
                    foundCustomer.setEmail(customer.getEmail());
                    customerRepo.save(foundCustomer);
                    message.set("Customer with ID: %s updated".formatted(customer.getId()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("Customer with ID: %s not found".formatted(customer.getId()));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }

    // Thymeleaf Delete: Delete customer by ID
    @Override
    public void deleteCustomerById(Long id) {
        customerRepo.findById(id).ifPresentOrElse(foundCustomer -> {
            if (!foundCustomer.getBookings().isEmpty()) {
                LOGGER.warn("Customer with ID: {} has booking history and thereby not deleted", id);
            }
            else {
                customerRepo.delete(foundCustomer);
                LOGGER.info("Customer with ID: {} deleted", id);
            }
        }, () -> LOGGER.warn("Customer with ID: {} not found", id));
    }
}