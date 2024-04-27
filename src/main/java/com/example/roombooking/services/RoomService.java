package com.example.roombooking.services;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;

import java.util.List;

public interface RoomService {


    RoomLiteDTO roomToMiniRoomDTO(Room room);

    Room minRoomDTOroom (RoomLiteDTO room);

    List<RoomLiteDTO> findAllRooms();

    RoomLiteDTO findRoomById(Long id);
}
