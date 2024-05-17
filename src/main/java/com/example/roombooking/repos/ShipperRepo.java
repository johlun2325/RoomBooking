package com.example.roombooking.repos;

import com.example.roombooking.models.External.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepo extends JpaRepository<Shipper, Long> {
}
