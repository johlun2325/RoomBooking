package com.example.roombooking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue
    private Long id;

//    @Size(min = 1, max = 50, message = "Room type must be between 1 and 50 characters")
//    @Pattern(regexp = "^[A-Öa-ö ]+$", message = "Room type can only contain Swedish letters and spaces")
    private String type;

//    @Min(value = 0, message = "Number of extra beds must be at least 0")
//    @Max(value = 4, message = "Number of extra beds must be no more than 4")
    private int extraBeds;

    public RoomType(String type, int extraBeds) {
        this.type = type;
        this.extraBeds = extraBeds;
    }
}
