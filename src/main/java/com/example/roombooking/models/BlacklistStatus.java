package com.example.roombooking.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistStatus {

    @JsonProperty("statusText")
    private String statusText;

    @JsonProperty("ok")
    private boolean isOk;
}
