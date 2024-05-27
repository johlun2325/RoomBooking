package com.example.roombooking.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventProperties {
    private String host;
    private String username;
    private String password;
    private String queue;
}
