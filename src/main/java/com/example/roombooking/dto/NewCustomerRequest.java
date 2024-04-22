package com.example.roombooking.dto;

import lombok.Data;

@Data
public class NewCustomerRequest {

    private String name;
    private String ssn;
    private String email;

}

/* JSON Exempel:
{
    "name" : "Will Smith",
    "ssn" : "990101-0069",
    "email" : "will.smith@jada.com"
}
*/
