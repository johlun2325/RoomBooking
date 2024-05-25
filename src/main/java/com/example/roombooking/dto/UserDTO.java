package com.example.roombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private UUID id;
    private String username;
    private String password;
    private boolean enabled;
    private String[] roleNames; // Is used because Thymeleaf can only send a String[] of an attribute from the Role class (We use name).

}
