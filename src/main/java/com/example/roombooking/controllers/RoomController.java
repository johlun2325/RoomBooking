package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // http://localhost:8080/room/search?startDate=2024-01-10&endDate=2024-01-15&numberOfPeople=1
    @GetMapping("/search")
    public List<RoomLiteDTO> searchRooms(@RequestParam String startDate,
                                          @RequestParam String endDate,
                                          @RequestParam int numberOfPeople) {
        return roomService.searchAvailableRooms(startDate, endDate, numberOfPeople);
    }

}
