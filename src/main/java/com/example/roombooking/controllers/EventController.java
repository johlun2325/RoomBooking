package com.example.roombooking.controllers;

import com.example.roombooking.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @RequestMapping("/all/{roomNo}")
    public String getMessages(@PathVariable String roomNo, Model model){

        List<String> messages = service.getAllMessagesByRoomNumber(roomNo);

        return "redirect:allrooms";
    }
}
