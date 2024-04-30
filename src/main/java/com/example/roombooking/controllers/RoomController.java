package com.example.roombooking.controllers;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
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
//    @GetMapping("/search")
//    public List<RoomLiteDTO> searchRooms(@RequestParam String startDate,
//                                          @RequestParam String endDate,
//                                          @RequestParam int numberOfPeople) {
//        return roomService.searchAvailableRooms(startDate, endDate, numberOfPeople);
//    }


    @RequestMapping("/search")
    public String list(@RequestParam String startDate,
                       @RequestParam String endDate,
                       @RequestParam int numberOfPeople,
                       Model model) {

        var availableRooms = roomService.searchAvailableRooms(startDate, endDate, numberOfPeople);
        model.addAttribute("availableRooms", availableRooms);

        return "searchForm";
    }



}
