package com.example.roombooking.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeedProperties {

    private String username1;
    private String username2;
    private String password;
    private String role1;
    private String role2;
}
