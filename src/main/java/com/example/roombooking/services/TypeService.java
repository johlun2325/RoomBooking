package com.example.roombooking.services;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.RoomType;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface TypeService {

    List<RoomType> findAllTypes();

    RoomType findTypeById(Long id);

    // HATEOAS: Not used
    CollectionModel<EntityModel<RoomType>> all();

    // HATEOAS: Not used
    EntityModel<RoomType> one(Long id);
}
