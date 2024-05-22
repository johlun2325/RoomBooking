package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import com.example.roombooking.utilities.DateUtility;
import com.example.roombooking.utilities.Utility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
    private final Utility dateUtility = new DateUtility();
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Override
    public RoomLiteDTO convertToRoomLiteDto(Room room) {
        return RoomLiteDTO.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    // Usage: When a new customer creates a new booking!
    @Override
    public Room convertLiteDtoToRoom(RoomLiteDTO room) {
        return Room.builder()
                .id(room.getId())
                .price(room.getPrice())
                .roomType(room.getRoomType())
                .build();
    }

    @Override
    public List<RoomLiteDTO> findAllRooms() {
        LOGGER.info("Loading all rooms");
        return roomRepo.findAll()
                .stream()
                .map(this::convertToRoomLiteDto)
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public RoomLiteDTO findRoomById(Long id) {
        return roomRepo.findById(id)
                .map(this::convertToRoomLiteDto)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<RoomLiteDTO> searchAvailableRooms(String startDate, String endDate, int numberOfPeople) {
        return roomRepo.findAll().stream()
                .filter(room -> room.getBookings().stream()
                        .noneMatch(booking -> dateUtility.areDatesOverlapping(
                                booking.getStartDate(),
                                booking.getEndDate(),
                                dateUtility.convertToLocalDate(startDate),
                                dateUtility.convertToLocalDate(endDate)))
                        && numberOfPeople <= room.getRoomType().getCapacity())
                .map(this::convertToRoomLiteDto)
                .collect(Collectors.toList());
    }
}
