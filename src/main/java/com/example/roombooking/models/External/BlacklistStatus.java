package com.example.roombooking.models.External;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistStatus {

    @JsonProperty("statusText")
    private String statusText;

    @JsonProperty("ok")
    private boolean isOk;
}
