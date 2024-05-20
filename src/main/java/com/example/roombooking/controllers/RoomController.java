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

    @GetMapping({"/book/{id}"})
    String bookRoom(@PathVariable Long id,
                    @RequestParam String startDate,
                    @RequestParam String endDate,
                    @RequestParam int numberOfPeople,
                    Model model) {

        RoomLiteDTO room = roomService.findRoomById(id);
        model.addAttribute("room", room);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pageTitle", "Ny Bokning");
        model.addAttribute("header", "Skapa ny bokning");
        model.addAttribute("ssnText", "Personnummer");
        model.addAttribute("startDateText", "Start datum");
        model.addAttribute("endDateText", "Slut datum");
        model.addAttribute("numberOfPeopleText", "Antal personer");
        model.addAttribute("roomIdText", "Room ID");
        model.addAttribute("roomPriceText", "Room Price");
        model.addAttribute("buttonText", "Boka");

        return "new-booking";
    }


    @RequestMapping("/search")
    public String list(@RequestParam String startDate,
                       @RequestParam String endDate,
                       @RequestParam int numberOfPeople,
                       Model model) {

        var availableRooms = roomService.searchAvailableRooms(startDate, endDate, numberOfPeople);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("pageTitle", "Sök Lediga Rum");
        model.addAttribute("header", "Sök rum");
        model.addAttribute("startDateText", "Start Datum");
        model.addAttribute("endDateText", "Slut Datum");
        model.addAttribute("numberOfPeopleText", "Antal personer");
        model.addAttribute("submitText", "Sök");
        model.addAttribute("idTh", "ID");
        model.addAttribute("roomTypeTh", "Rum Type");
        model.addAttribute("priceTh", "Pris");
        model.addAttribute("bookTh", "Boka");
        model.addAttribute("buttonBookText", "Boka nu");
        model.addAttribute("roomTypeTh", "Rum Type");

        return "searchForm";
    }



}
