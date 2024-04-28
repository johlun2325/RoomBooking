package com.example.roombooking.services;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface RoomService {


    Room convertDtoToRoom(RoomLiteDTO room);

    RoomLiteDTO convertToRoomLiteDto(Room room);

    List<RoomLiteDTO> findAllRooms();

    RoomLiteDTO findRoomById(Long id);

    // HATEOAS: Not used
    CollectionModel<EntityModel<RoomLiteDTO>> all();

    // HATEOAS: Not used
    EntityModel<RoomLiteDTO> one(Long id);
}
