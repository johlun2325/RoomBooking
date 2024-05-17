package com.example.roombooking.models.Messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("RoomCleaningFinished")
public class RoomCleaningFinished extends Message{

    @JsonProperty("CleaningByUser")
    @Column(name = "cleaning_by_user", nullable = true)
    public String cleaningByUser;
    @Override
    public void getMessage() {

    }
}
