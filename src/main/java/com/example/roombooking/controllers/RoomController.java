package com.example.roombooking.controllers;

import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;
    private final RoomRepo roomRepo;


//    private Room findRoom(Long id) {
//        return roomRepo.findById(id).orElseThrow(() -> {
//            final String WARNING_MESSAGE = "No room with ID: %s was found".formatted(id);
//            logger.warn(WARNING_MESSAGE);
//            return new NoSuchElementException(WARNING_MESSAGE);
//        });
//    }
//
//    @GetMapping()
//    CollectionModel<EntityModel<Room>> all() {
//
//        List<EntityModel<Room>> rooms = roomRepo.findAll().stream()
//                .map(room -> EntityModel.of(room,
//                        linkTo(methodOn(RoomController.class).one(room.getId())).withSelfRel(),
//                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")))
//                .toList();
//
//        return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).all()).withSelfRel());
//    }
//
//    @GetMapping("/{id}")
//    EntityModel<Room> one(@PathVariable Long id) {
//
//        return EntityModel.of(findRoom(id),
//                linkTo(methodOn(RoomController.class).one(id)).withSelfRel(),
//                linkTo(methodOn(RoomController.class).all()).withRel("rooms"));
//    }



}
