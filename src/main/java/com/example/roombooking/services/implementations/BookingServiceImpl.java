package com.example.roombooking.services.implementations;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.BookingLiteDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.*;
import com.example.roombooking.models.External.BlacklistStatus;
import com.example.roombooking.repos.BookingRepo;
import com.example.roombooking.repos.CustomerRepo;
import com.example.roombooking.repos.RoomRepo;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.utilities.DateStrategy;
import com.example.roombooking.utilities.DateUtility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final CustomerRepo customerRepo;
    private final RoomRepo roomRepo;
    private final DateUtility dateUtility = new DateStrategy();
    private final BlacklistService blacklistService = new BlacklistService();
    private final DiscountService discountService = new DiscountService();
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
                .totalPrice(booking.getTotalPrice())
                .build();
    }

    //Booking till BookingDTO
    @Override
    public BookingDTO convertToDto(Booking booking) {
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
                .totalPrice(booking.getTotalPrice())
                .build();
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        LOGGER.info("Loading all bookings");
        return bookingRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public BookingDTO findBookingById(Long id) {
        LOGGER.info("Loading booking with ID: {}", id);
        return bookingRepo.findById(id)
                .map(this::convertToDto)
                .orElseThrow(NoSuchElementException::new);
    }

    // TODO: Fungerar bara med befintliga kunder
    @Override
    public void addBooking(BookingDTO booking) {
        Customer customer = customerRepo.findCustomerBySsn(booking.getCustomer().getSsn()).orElseThrow(NoSuchElementException::new);
        Room room = roomRepo.findById(booking.getRoom().getId()).orElseThrow(NoSuchElementException::new);

        BlacklistStatus status = blacklistService.fetchBlacklistedStatusByEmail(customer.getEmail());

        if (status.isOk()) {
            Booking newBooking = new Booking(customer, room, booking.getNumberOfPeople(), booking.getStartDate(), booking.getEndDate());
            discountService.applyDiscounts(newBooking);
            bookingRepo.save(newBooking);
            LOGGER.info("Booking add");
            return;
        }
        LOGGER.warn("Customer is blacklisted");
    }

    @Override
    public void updateBooking(BookingDTO booking) {
        bookingRepo.findById(booking.getId()).ifPresentOrElse(foundBooking -> {

            int capacity = foundBooking.getRoom().getRoomType().getCapacity();
            int numberOfPeople = booking.getNumberOfPeople();

            if (numberOfPeople > capacity) {
                LOGGER.warn("The number of people exceeds the capacity.");
                return;
            }

            // Use updated dates from the DTO
            LocalDate updatedStartDate = booking.getStartDate();
            LocalDate updatedEndDate = booking.getEndDate();

            var bookings = bookingRepo.findAll();
            boolean areBookingDatesOverlapping = bookings.stream()
                    .filter(b -> !b.getId().equals(foundBooking.getId()))
                    .filter(b -> b.getRoom().getId().equals(foundBooking.getRoom().getId()))
                    .anyMatch(b -> dateUtility.areDatesOverlapping(
                            updatedStartDate,
                            updatedEndDate,
                            b.getStartDate(),
                            b.getEndDate()));

            if (areBookingDatesOverlapping) {
                LOGGER.warn("The chosen dates are overlapping with the existing dates");
                return;
            }

            foundBooking.setNumberOfPeople(numberOfPeople);
            foundBooking.setStartDate(updatedStartDate);
            foundBooking.setEndDate(updatedEndDate);

            bookingRepo.delete(foundBooking);               // Using delete for LoyaltyDiscount logic (needs to exclude the updated booking)
            discountService.applyDiscounts(foundBooking);
            bookingRepo.save(foundBooking);

            LOGGER.info("Booking with ID: {} updated", foundBooking.getId());

        }, () -> LOGGER.warn("Booking with ID: {} not found", booking.getId()));
    }


    //delete by id with thymeleaf
    @Override
    public void deleteBookingById(Long id) {
        bookingRepo.findById(id).ifPresentOrElse(foundBooking -> {
            bookingRepo.delete(foundBooking);
            LOGGER.info("Booking with ID: {} deleted", id);
        }, () -> LOGGER.warn("Booking with ID: {} not found", id));
    }
}
