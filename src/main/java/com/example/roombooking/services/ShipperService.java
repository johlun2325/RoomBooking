package com.example.roombooking.services;

import com.example.roombooking.models.External.Shipper;

public interface ShipperService  {

    Shipper[] fetchShippers(String url);

}
