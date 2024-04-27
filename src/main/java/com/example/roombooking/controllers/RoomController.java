package com.example.roombooking.controllers;

import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomRepo roomRepo;


//    @GetMapping()
//    List<RoomLiteDTO> getAllCustomers() {
//        return roomService.findAllRooms();
//    }
//
//    @GetMapping({"/{id}"})
//    RoomLiteDTO getCustomer(@PathVariable Long id) {
//        return roomService.findRoomById(id);
//    }



//    @GetMapping()
//    CollectionModel<EntityModel<Room>> findAllRooms() {
//
//        List<EntityModel<Room>> rooms = roomRepo.findAll().stream()
//                .map(room -> EntityModel.of(room,
//                        linkTo(methodOn(RoomController.class).one(room.getId())).withSelfRel(),
//                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")))
//                .toList();
//
//        return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).all()).withSelfRel());
//    }

//    @GetMapping("/{id}")
//    EntityModel<Room> findRoomById(@PathVariable Long id) {
//
//        return EntityModel.of((id),
//                linkTo(methodOn(RoomController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(RoomController.class).all()).withRel("rooms"));
//    }

}
