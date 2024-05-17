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
@DiscriminatorValue("RoomOpened")
public class RoomOpened extends Message{

    @Override
    public String getMessage() {
        return "Dörren öppnad " + getTimeStamp();
    }
}
