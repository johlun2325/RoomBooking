package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.MiniRoomDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl  implements RoomService {

    private final RoomRepo roomRepo;

    @Override
    public MiniRoomDTO roomToMiniRoomDTO(Room room) {
        return MiniRoomDTO.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public Room minRoomDTOroom(MiniRoomDTO room) {
        return Room.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public List<MiniRoomDTO> findAllRooms() {
        return roomRepo.findAll()
                .stream()
                .map(this::roomToMiniRoomDTO)
                .toList();
    }

    @Override
    public MiniRoomDTO findRoomById(Long id) {
        return roomRepo.findById(id)
                .map(this::roomToMiniRoomDTO)
                .orElseThrow(NoSuchElementException::new);
    }
}
