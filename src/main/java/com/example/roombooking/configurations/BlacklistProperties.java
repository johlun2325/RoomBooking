package com.example.roombooking.configurations;


import lombok.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistProperties {

    private String url;

}
