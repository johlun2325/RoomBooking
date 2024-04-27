package com.example.roombooking.services.implementations;

import com.example.roombooking.controllers.CustomerController;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Override
    public Customer convertDtoToCustomer(CustomerDTO customer) {
        return Customer.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public CustomerLiteDTO convertToCustomerLiteDto(Customer customer) {
        return CustomerLiteDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .ssn(customer.getSsn())
                .email(customer.getEmail())
                .build();
    }

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

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepo.findAll()
                .stream()
                .map(this::convertToCustomerDto)
                .peek(customer -> LOGGER.info("Customer data listed: ID %S".formatted(customer.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public CustomerDTO findCustomerById(Long id) {
        return customerRepo.findById(id)
                .map(this::convertToCustomerDto)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String addCustomer(CustomerDTO customer) {
        var message = new AtomicReference<String>();
        return customerRepo.findCustomerBySsn(customer.getSsn())
                .map(foundCustomer -> {
                    message.set("Customer with SSN: %s exists".formatted(customer.getSsn()));
                    LOGGER.warn(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    customerRepo.save(convertDtoToCustomer(customer));
                    message.set("Customer with SSN: %s added".formatted(customer.getSsn()));
                    LOGGER.info(message.get());
                    return message.get();
                });
    }

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

    @Override
    public String deleteCustomer(CustomerDTO customer) {
        var message = new AtomicReference<String>();
        return customerRepo.findById(customer.getId())
                .map(foundCustomer -> {
                    if (!foundCustomer.getBookings().isEmpty()) {
                        message.set("Customer with ID: %s has booking history and thereby not deleted"
                                .formatted(customer.getId()));
                        LOGGER.warn(message.get());
                        return message.get();
                    }
                    customerRepo.delete(foundCustomer);
                    message.set("Customer with ID: %s deleted".formatted(customer.getId()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("Customer with ID: %s not found".formatted(customer.getId()));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }

    //thymeleaf delete
    @Override
    public void deleteCustomerById(Long id) {
        customerRepo.deleteById(id);
    }

    // HATEOAS: Not used
    @Override
    public CollectionModel<EntityModel<CustomerDTO>> all() {
        List<EntityModel<CustomerDTO>> customers = findAllCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerServiceImpl.class).one(customer.getId())).withSelfRel(),
                        linkTo(methodOn(CustomerServiceImpl.class).all()).withRel("customers")))
                .toList();

        return CollectionModel.of(customers, linkTo(methodOn(CustomerServiceImpl.class).all()).withSelfRel());
    }

    // HATEOAS: Not used
    @Override
    public EntityModel<CustomerDTO> one(Long id) {
        CustomerDTO customer = findCustomerById(id);

        return EntityModel.of(customer,
                linkTo(methodOn(CustomerServiceImpl.class).one(id)).withSelfRel(),
                linkTo(methodOn(CustomerServiceImpl.class).all()).withRel("customers"));
    }
}