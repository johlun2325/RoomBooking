package com.example.roombooking.repos;

import com.example.roombooking.models.BusinessCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCustomerRepo extends JpaRepository<BusinessCustomer, Long> {

}
