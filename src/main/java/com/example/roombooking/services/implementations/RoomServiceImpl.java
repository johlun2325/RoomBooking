package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.MiniRoomDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
