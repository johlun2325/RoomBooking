package com.example.roombooking.controllers;

import com.example.roombooking.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @RequestMapping("/all/{roomNo}")
    public List<String> getMessages(@PathVariable String roomNo){
        return service.getAllMessagesByRoomNumber(roomNo);
    }
}
