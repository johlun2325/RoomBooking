package com.example.roombooking.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private int extraBeds;

//    @OneToMany(mappedBy = "roomType")
//    private List<Room> rooms;

    public RoomType(String type, int extraBeds) {
        this.type = type;
        this.extraBeds = extraBeds;
//        this.rooms = new ArrayList<>();
    }

//    public void addRoom(Room room) {
//        boolean notFound = rooms.stream().noneMatch(it -> it.equals(room));
//        if (notFound) {
//            rooms.add(room);
//        }
//    }
//
//    public void removeRoom(Room booking) {
//        rooms.removeIf(it -> it.equals(booking));
//    }

}
