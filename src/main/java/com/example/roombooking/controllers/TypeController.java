package com.example.roombooking.controllers;

import com.example.roombooking.models.RoomType;
import com.example.roombooking.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping()
    List<RoomType> getAllTypes() {
        return typeService.findAllTypes();
    }

    @GetMapping({"/{id}"})
    RoomType getType(@PathVariable Long id) {
        return typeService.findTypeById(id);
    }

}
