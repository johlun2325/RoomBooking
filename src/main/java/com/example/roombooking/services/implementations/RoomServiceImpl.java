package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl  implements RoomService {

    private final RoomRepo roomRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public RoomLiteDTO roomToMiniRoomDTO(Room room) {
        return RoomLiteDTO.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public Room minRoomDTOroom(RoomLiteDTO room) {
        return Room.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public List<RoomLiteDTO> findAllRooms() {
        return roomRepo.findAll()
                .stream()
                .map(this::roomToMiniRoomDTO)
                .peek(room -> LOGGER.info("Room data listed: ID %S".formatted(room.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public RoomLiteDTO findRoomById(Long id) {
        return roomRepo.findById(id)
                .map(this::roomToMiniRoomDTO)
                .orElseThrow(NoSuchElementException::new);
    }
}
