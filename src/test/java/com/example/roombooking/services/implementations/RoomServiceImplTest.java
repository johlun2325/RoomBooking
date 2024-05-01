package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.RoomRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {


    @Mock
    private RoomRepo repo;

    private RoomServiceImpl service = new RoomServiceImpl(repo);

    private Long id = 1L;
    private double price = 100.0;
    private RoomType roomType = new RoomType();
    private List<Booking> bookings = new ArrayList<>();

    private Room room = new Room(id, price, roomType, bookings);

    private RoomLiteDTO liteRoom = new RoomLiteDTO().builder()
            .id(room.getId())
            .price(room.getPrice())
            .roomType(room.getRoomType()).build();

    @Test
    void convertToRoomLiteDto() {
        RoomLiteDTO actual = service.convertToRoomLiteDto(room);

        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getPrice(), room.getPrice());
        assertEquals(actual.getRoomType(), room.getRoomType());

    }


    @Test
    void convertLiteDtoToRoom() {
        Room actual = service.convertLiteDtoToRoom(liteRoom);

        assertEquals(actual.getId(), room.getId());
        assertEquals(actual.getPrice(), room.getPrice());
        assertEquals(actual.getRoomType(), room.getRoomType());
    }

    @Test
    void findAllRooms() {
    }

    @Test
    void findRoomById() {
    }

    @Test
    void searchAvailableRooms() {
    }
}