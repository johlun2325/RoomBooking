package com.example.roombooking.services;

import com.example.roombooking.models.ContractCustomer;
import com.example.roombooking.models.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipperService  {

    Shipper[] fetchShippers(String url);

}
