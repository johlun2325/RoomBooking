package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepo roomRepo;
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
        return roomRepo.findAll()
                .stream()
                .map(this::convertToRoomLiteDto)
                .peek(room -> LOGGER.info("Room data listed: ID %S".formatted(room.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public RoomLiteDTO findRoomById(Long id) {
        return roomRepo.findById(id)
                .map(this::convertToRoomLiteDto)
                .orElseThrow(NoSuchElementException::new);
    }

    private boolean areDatesOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        long overlap = Math.min(end1.toEpochDay(), end2.toEpochDay()) -
                Math.max(start1.toEpochDay(), start2.toEpochDay());

        return overlap >= 0;
    }

    private LocalDate convertToLocalDate(String date) {
        Pattern datePattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})$");

        Matcher matcher = datePattern.matcher(date);
        if (!matcher.find()) throw new RuntimeException("Illegal date format");

        int yyyy = Integer.parseInt(matcher.group(1));
        int mm = Integer.parseInt(matcher.group(2));
        int dd = Integer.parseInt(matcher.group(3));

        return LocalDate.of(yyyy, mm, dd);
    }

    @Override
    public List<RoomLiteDTO> searchAvailableRooms(String startDate, String endDate, int numberOfPeople) {
        return roomRepo.findAll().stream()
                .filter(room -> room.getBookings().stream()
                        .noneMatch(booking -> areDatesOverlapping(
                                booking.getStartDate(),
                                booking.getEndDate(),
                                convertToLocalDate(startDate),
                                convertToLocalDate(endDate)))
                        && room.getRoomType().getExtraBeds() + room.getRoomType().getCapacity() <= numberOfPeople)
                .map(this::convertToRoomLiteDto)
                .collect(Collectors.toList());
    }

}
