package com.example.roombooking.repos;

import com.example.roombooking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerBySsn(String ssn);

}
