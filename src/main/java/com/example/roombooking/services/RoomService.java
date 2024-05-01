package com.example.roombooking.services;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    // Usage: When a new customer creates a new booking!
    Room convertLiteDtoToRoom(RoomLiteDTO room);

    RoomLiteDTO convertToRoomLiteDto(Room room);

    List<RoomLiteDTO> findAllRooms();

    RoomLiteDTO findRoomById(Long id);

    List<RoomLiteDTO> searchAvailableRooms(String startDate, String endDate, int numberOfPeople);

    LocalDate convertToLocalDate(String date);

    boolean areDatesOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);
}