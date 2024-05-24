package com.example.roombooking.dto;

import com.example.roombooking.security.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
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
    private Collection<Role> roles;

}
