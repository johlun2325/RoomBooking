package com.example.roombooking.dto;

import lombok.Data;

@Data
public class CustomerUpdateEmailRequest {

    private Long id;
    private String email;

}

/* JSON Exempel:
{
    "id" : "3",
    "price" : "9995"
}
*/