package com.example.roombooking.models.External;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blacklisted {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("group")
    private String group;

    @JsonProperty("created")
    private LocalDateTime created = LocalDateTime.now();

    @JsonProperty("ok")
    private boolean isOk;

}
