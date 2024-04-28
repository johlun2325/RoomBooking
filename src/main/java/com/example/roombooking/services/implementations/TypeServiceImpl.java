package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.TypeRepo;
import com.example.roombooking.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepo typeRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeServiceImpl.class);


    @Override
    public List<RoomType> findAllTypes() {
        return typeRepo.findAll()
                .stream()
                .peek(room -> LOGGER.info("Type data listed: ID %S".formatted(room.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public RoomType findTypeById(Long id) {
        return typeRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    // HATEOAS: Not used
    @Override
    public CollectionModel<EntityModel<RoomType>> all() {
        List<EntityModel<RoomType>> types = findAllTypes().stream()
                .map(type -> EntityModel.of(type,
                        linkTo(methodOn(TypeServiceImpl.class).one(type.getId())).withSelfRel(),
                        linkTo(methodOn(TypeServiceImpl.class).all()).withRel("types")))
                .toList();

        return CollectionModel.of(types, linkTo(methodOn(TypeServiceImpl.class).all()).withSelfRel());
    }

    // HATEOAS: Not used
    @Override
    public EntityModel<RoomType> one(Long id) {
        RoomType type = findTypeById(id);

        return EntityModel.of(type,
                linkTo(methodOn(TypeServiceImpl.class).one(id)).withSelfRel(),
                linkTo(methodOn(TypeServiceImpl.class).all()).withRel("types"));
    }
}
