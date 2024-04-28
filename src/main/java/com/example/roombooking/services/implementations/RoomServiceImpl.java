package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl  implements RoomService {

    private final RoomRepo roomRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public RoomLiteDTO convertToRoomLiteDto(Room room) {
        return RoomLiteDTO.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    // Usage: When a new customer creates a new booking!
    @Override
    public Room convertLiteDtoToRoom(RoomLiteDTO room) {
        return Room.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public List<RoomLiteDTO> findAllRooms() {
        return roomRepo.findAll()
                .stream()
                .map(this::convertToRoomLiteDto)
                .peek(room -> LOGGER.info("Room data listed: ID %S".formatted(room.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public RoomLiteDTO findRoomById(Long id) {
        return roomRepo.findById(id)
                .map(this::convertToRoomLiteDto)
                .orElseThrow(NoSuchElementException::new);
    }

    // HATEOAS: Not used
    @Override
    public CollectionModel<EntityModel<RoomLiteDTO>> all() {
        List<EntityModel<RoomLiteDTO>> customers = findAllRooms().stream()
                .map(room -> EntityModel.of(room,
                        linkTo(methodOn(RoomServiceImpl.class).one(room.getId())).withSelfRel(),
                        linkTo(methodOn(RoomServiceImpl.class).all()).withRel("rooms")))
                .toList();

        return CollectionModel.of(customers, linkTo(methodOn(RoomServiceImpl.class).all()).withSelfRel());
    }

    // HATEOAS: Not used
    @Override
    public EntityModel<RoomLiteDTO> one(Long id) {
        RoomLiteDTO room = findRoomById(id);

        return EntityModel.of(room,
                linkTo(methodOn(RoomServiceImpl.class).one(id)).withSelfRel(),
                linkTo(methodOn(RoomServiceImpl.class).all()).withRel("rooms"));
    }

}
