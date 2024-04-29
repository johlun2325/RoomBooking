package com.example.roombooking.services;

import com.example.roombooking.models.RoomType;

import java.util.List;

public interface TypeService {

    List<RoomType> findAllTypes();

    RoomType findTypeById(Long id);

}
