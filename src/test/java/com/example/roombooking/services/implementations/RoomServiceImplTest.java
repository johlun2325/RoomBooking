package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Room;
import com.example.roombooking.models.RoomType;
import com.example.roombooking.repos.RoomRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {


    @Mock
    private RoomRepo repo;

    @InjectMocks
    private RoomServiceImpl service = new RoomServiceImpl(repo);

    private final Long id = 1L;
    private final BigDecimal price = BigDecimal.valueOf(100.0);
    private final RoomType roomType = new RoomType();
    private final List<Booking> bookings = new ArrayList<>();

    private final Room room = new Room(id, price, roomType, bookings);

    private final RoomLiteDTO liteRoom = new RoomLiteDTO().builder()
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

        when(repo.findAll()).thenReturn(Arrays.asList(room));
        RoomServiceImpl serv = new RoomServiceImpl(repo);

        List<RoomLiteDTO> allRooms = serv.findAllRooms();
        assertEquals(1, allRooms.size());

    }

    @Test
    void findRoomById() {

        when(repo.findById(room.getId())).thenReturn(Optional.of(room));
        RoomServiceImpl serv = new RoomServiceImpl(repo);
        RoomLiteDTO foundRoom = serv.findRoomById(room.getId());

        assertEquals(room.getId(), foundRoom.getId());
        assertEquals(room.getPrice(), foundRoom.getPrice());
        assertEquals(room.getRoomType(), foundRoom.getRoomType());

    }

    @Test
    void searchAvailableRooms() {

    }
}