package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.Customer;
import com.example.roombooking.models.Room;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.services.CustomerService;
import com.example.roombooking.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    //Booking till BookingLiteDTO
    @Override
    public BookingLiteDTO convertToBookingLiteDto(Booking booking) {
        return BookingLiteDTO.builder()
                .id(booking.getId())
                .room(new RoomLiteDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    //Booking till BookingDTO
    @Override
    public BookingDTO convertDtoToBooking(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .customer(new CustomerLiteDTO(
                        booking.getCustomer().getId(),
                        booking.getCustomer().getName(),
                        booking.getCustomer().getSsn(),
                        booking.getCustomer().getEmail()))
                .room(new RoomLiteDTO(
                        booking.getRoom().getId(),
                        booking.getRoom().getPrice(),
                        booking.getRoom().getRoomType()))
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    //BookingDTO till booking - kolla om funkar
    @Override
    public Booking bookingDTOToBooking(BookingDTO booking) {


        Customer c = customerRepo.findById(booking.getCustomer().getId()).orElseThrow(NoSuchElementException::new);
        Room r = roomRepo.findById(booking.getRoom().getId()).get();

        return Booking.builder()
                .id(booking.getId())
                .numberOfPeople(booking.getNumberOfPeople())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(c)
                .room(r).build();
    }


    @Override
    public List<BookingDTO> findAllBookings() {
        return bookingRepo.findAll()
                .stream()
                .map(this::convertDtoToBooking)
                .peek(booking -> LOGGER.info("Booking data listed: ID %S".formatted(booking.getId())))
                .toList();
    }

    // TODO: No LOGGER here
    @Override
    public BookingDTO findBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(this::convertDtoToBooking)
                .orElseThrow(NoSuchElementException::new);
    }

    //delete by id with thymeleaf
    @Override
    public String deleteBookingById(Long id) {
        bookingRepo.deleteById(id);
        return "Booking with id " + id + " deleted";
    }

    @Override
    public String addBooking(BookingDTO booking) {
        var message = new AtomicReference<String>();
        return bookingRepo.findById(booking.getId())
                .map(foundBooking -> {
                    foundBooking.setNumberOfPeople(booking.getNumberOfPeople());
                    bookingRepo.save(foundBooking);
                    message.set("Customer with ID: %s updated".formatted(booking.getId()));
                    LOGGER.info(message.get());
                    return message.get();
                })
                .orElseGet(() -> {
                    message.set("Booking with ID: %s not found".formatted(booking.getId()));
                    LOGGER.warn(message.get());
                    return message.get();
                });
    }

    @Override
    public Booking convertDtoToBooking(BookingDTO booking) {
        return null;
    }
}
