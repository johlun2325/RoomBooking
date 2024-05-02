package com.example.roombooking.services.implementations;

import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.TypeRepo;
import com.example.roombooking.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

}
