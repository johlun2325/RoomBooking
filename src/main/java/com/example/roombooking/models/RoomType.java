package com.example.roombooking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String type;
    private int extraBeds;

    public RoomType(String type, int extraBeds) {
        this.type = type;
        this.extraBeds = extraBeds;
    }
}
