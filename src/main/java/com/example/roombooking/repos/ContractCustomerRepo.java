package com.example.roombooking.repos;

import com.example.roombooking.models.External.ContractCustomer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {

    Optional<ContractCustomer> findByExternalId(Long id);

    List<ContractCustomer> findAllByCompanyNameStartingWith(String query, Sort sort);

//    List<ContractCustomer> findAllByCompanyNameStartingWithOrContactNameStartingWithOrCountryStartingWith(
//            String companyName,
//            String contactName,
//            String country,
//            Sort sort);

}
