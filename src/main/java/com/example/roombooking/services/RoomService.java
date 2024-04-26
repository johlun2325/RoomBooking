package com.example.roombooking.services;

import com.example.roombooking.dto.MiniRoomDTO;
import com.example.roombooking.models.Room;

import java.util.List;

public interface RoomService {


    MiniRoomDTO roomToMiniRoomDTO(Room room);

    Room minRoomDTOroom (MiniRoomDTO room);

    List<MiniRoomDTO> findAllRooms();

    MiniRoomDTO findRoomById(Long id);
}
