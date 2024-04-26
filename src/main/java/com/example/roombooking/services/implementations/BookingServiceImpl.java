package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.MiniBookingDTO;
import com.example.roombooking.dto.MiniCustomerDTO;
import com.example.roombooking.dto.MiniRoomDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


    private final BookingRepo bookingRepo;

    @Override
    public MiniBookingDTO bookingToMiniBookingDTO(Booking booking) {
        return MiniBookingDTO.builder()
                .id(booking.getId())
                .room(new MiniRoomDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public BookingDTO bookingToBookingDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .customer(new MiniCustomerDTO(
                        booking.getCustomer().getId(),
                        booking.getCustomer().getName(),
                        booking.getCustomer().getSsn(),
                        booking.getCustomer().getEmail()))
                .room(new MiniRoomDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public List<BookingDTO> getAllBookingDTOs() {
        return bookingRepo.findAll()
                .stream()
                .map(this::bookingToBookingDTO)
                .toList();
    }

    @Override
    public BookingDTO getBookingDTO(Long id) {
        return bookingRepo.findById(id)
                .map(this::bookingToBookingDTO)
                .orElseThrow(NoSuchElementException::new);
    }
}
