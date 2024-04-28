package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping()
    List<RoomLiteDTO> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping({"/{id}"})
    RoomLiteDTO getRoom(@PathVariable Long id) {
        return roomService.findRoomById(id);
    }

//    // HATEOAS: Not used
//    @GetMapping()
//    CollectionModel<EntityModel<RoomLiteDTO>> findAllRooms() {
//        return roomService.all();
//    }
//
//    // HATEOAS: Not used
//    @GetMapping("/{id}")
//    EntityModel<RoomLiteDTO> findRoomById(@PathVariable Long id) {
//        return roomService.one(id);
//    }

}
