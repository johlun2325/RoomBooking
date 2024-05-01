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
import java.util.Map;

@Controller
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping()
    List<RoomLiteDTO> getAllRooms() {
        return roomService.findAllRooms();
    }

    @GetMapping({"/book/{id}"})
    String bookRoom(@PathVariable Long id,
                    @RequestParam String startDate,
                    @RequestParam String endDate,
                    @RequestParam int numberOfPeople,
                    Model model) {

        RoomLiteDTO room = roomService.findRoomById(id);
        model.addAllAttributes(Map.of(
                "room", room,
                "numberOfPeople", numberOfPeople,
                "startDate", startDate,
                "endDate", endDate));

        return "new-booking";
    }


    @RequestMapping("/search")
    public String list(@RequestParam String startDate,
                       @RequestParam String endDate,
                       @RequestParam int numberOfPeople,
                       Model model) {

        var availableRooms = roomService.searchAvailableRooms(startDate, endDate, numberOfPeople);
        model.addAllAttributes(Map.of(
                "availableRooms", availableRooms,
                "numberOfPeople", numberOfPeople,
                "startDate", startDate,
                "endDate", endDate));

        return "searchForm";
    }



}
