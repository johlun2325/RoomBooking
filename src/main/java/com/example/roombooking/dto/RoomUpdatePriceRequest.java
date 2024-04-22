package com.example.roombooking.dto;

import lombok.Data;

@Data
public class RoomUpdatePriceRequest {

    private Long id;
    private double price;
}

/* JSON Exempel:
{
    "id" : "1",
    "price" : "120000.95"
}
*/