package com.example.roombooking.repos;

import com.example.roombooking.models.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractCustomerRepo extends JpaRepository<ContractCustomer, Long> {

    Page<ContractCustomer> findAllByCompanyNameStartingWith(String query, Pageable pageable);



//    List<ContractCustomer> findAllByCompanyNameStartingWithOrContactNameStartingWithOrCountryStartingWith(
//            String companyName,
//            String contactName,
//            String country,
//            Sort sort);

}
