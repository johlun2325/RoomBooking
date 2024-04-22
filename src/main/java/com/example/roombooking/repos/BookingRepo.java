package com.example.roombooking.repos;

import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    boolean existsByCustomer(Customer customer);

}
