package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoomUpdatePriceRequest;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepo roomRepo;
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    private Room findRoom(Long id) {
        return roomRepo.findById(id).orElseThrow(() -> {
            final String WARNING_MESSAGE = "No room with ID: %s was found".formatted(id);
            logger.warn(WARNING_MESSAGE);
            return new NoSuchElementException(WARNING_MESSAGE);
        });
    }

    @GetMapping()
    CollectionModel<EntityModel<Room>> all() {

        List<EntityModel<Room>> rooms = roomRepo.findAll().stream()
                .map(room -> EntityModel.of(room,
                        linkTo(methodOn(RoomController.class).one(room.getId())).withSelfRel(),
                        linkTo(methodOn(RoomController.class).all()).withRel("rooms")))
                .toList();

        return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    EntityModel<Room> one(@PathVariable Long id) {

        return EntityModel.of(findRoom(id),
                linkTo(methodOn(RoomController.class).one(id)).withSelfRel(),
                linkTo(methodOn(RoomController.class).all()).withRel("rooms"));
    }

    @PutMapping("/updatePrice")
    CollectionModel<EntityModel<Room>> updatePrice(@RequestBody RoomUpdatePriceRequest roomUpdatePriceRequest) {

        Room room = findRoom(roomUpdatePriceRequest.getId());
        room.setPrice(roomUpdatePriceRequest.getPrice());
        roomRepo.save(room);
        logger.info("Room with ID {} price updated: {}", room.getId(), room.getPrice());

        return all();
    }

}
