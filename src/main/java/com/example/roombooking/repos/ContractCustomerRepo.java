package com.example.roombooking.repos;

import com.example.roombooking.models.ContractCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {



}
