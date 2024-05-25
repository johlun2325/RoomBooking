package com.example.roombooking.dto;

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
    private Collection<RoleDTO> roles;

    public UserDTO(String username, String password, boolean enabled, Collection<RoleDTO> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
}
