package com.example.roombooking.models.Events;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("RoomClosed")
public class RoomClosed extends Message{
    @Override
    public String getMessage() {
        return "Dörren stängd " + getTimeStamp();
    }
}
