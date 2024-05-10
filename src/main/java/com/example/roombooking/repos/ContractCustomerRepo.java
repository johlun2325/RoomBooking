package com.example.roombooking.repos;

import com.example.roombooking.models.ContractCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {


    List<ContractCustomer> findAllByContactNameContaining(String contractName, Sort sort);

    List<ContractCustomer> findAllBy(Sort sort);

}
