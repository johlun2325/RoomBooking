package com.example.roombooking.models.Events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosed.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class, name = "RoomCleaningFinished"),
        @JsonSubTypes.Type(value = RoomOpened.class, name = "RoomOpened"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class, name = "RoomCleaningStarted")
})
public abstract class Message {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("TimeStamp")
    @Column(name = "time_stamp", nullable = false)
    public Date timeStamp;

    @JsonProperty("RoomNo")
    @Column(name = "room_no", nullable = false)
    public String roomNo;

    public abstract String getMessage();

}
