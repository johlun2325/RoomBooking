package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.models.EmailConfirmation;
import com.example.roombooking.repos.EmailConfirmationRepo;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.services.implementations.EmailService;
import com.example.roombooking.utilities.DateStrategy;
import com.example.roombooking.utilities.DateUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
class BookingController {

    @Autowired
    private TemplateEngine templateEngine;
    private final BookingService bookingService;
    private final EmailService emailService;
    private final DateUtility dateUtility = new DateStrategy();
    private final EmailConfirmationRepo emailConfirmationRepo;

    @GetMapping("/all")
    String getAllBookings(Model model) {
        List<BookingDTO> all = bookingService.findAllBookings();
        model.addAttribute("allBookings", all);
        model.addAttribute("pageHeader", "Bokningar");
        model.addAttribute("header", "Alla bokningar");
        model.addAttribute("bookingId", "Boknings-ID");
        model.addAttribute("customerId", "Kund-ID");
        model.addAttribute("roomId", "Rums-ID");
        model.addAttribute("roomType", "Rums-typ");
        model.addAttribute("totalPrice", "Totalpris");
        model.addAttribute("startDate", "Incheckning");
        model.addAttribute("endDate", "Utcheckning");
        model.addAttribute("numberOfPeople", "Antal personer");
        model.addAttribute("update", "Uppdatera");
        model.addAttribute("delete", "Ta bort");
        model.addAttribute("hem", "Hem");

        return "booking/bookings.html";
    }

    @GetMapping({"/{id}"})
    BookingDTO getAllBookings(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @PostMapping("/add")
    public String addBooking(@RequestParam String ssn,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             @RequestParam int numberOfPeople,
                             @RequestParam Long roomId,
                             @RequestParam double roomPrice,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        BookingDTO bookingDTO = new BookingDTO(new CustomerLiteDTO(ssn),
                                               new RoomLiteDTO(roomId, roomPrice),
                                               numberOfPeople,
                                               dateUtility.convertToLocalDate(startDate),
                                               dateUtility.convertToLocalDate(endDate));

        Booking newBooking = bookingService.addBooking(bookingDTO);
        if (newBooking != null) {
            redirectAttributes.addFlashAttribute("newBooking", newBooking);
            return "redirect:/booking/send-confirmation";
        }
        model.addAttribute("message", "Customer is blacklisted. No booking was added");

        return "index.html";
    }

    @RequestMapping("/send-confirmation")
    public String sendConfirmationEmail(@ModelAttribute("newBooking") Booking newBooking) {

        EmailConfirmation confirmation = emailConfirmationRepo.findByName("Booking Confirmation Message")
                .orElseThrow(NoSuchElementException::new);

        Context context = new Context();
        context.setVariable("customerName", newBooking.getCustomer().getName());
        context.setVariable("checkInDate", newBooking.getStartDate());
        context.setVariable("checkOutDate", newBooking.getEndDate());
        context.setVariable("roomType", newBooking.getRoom().getRoomType().getType());
        context.setVariable("numberOfPeople", newBooking.getNumberOfPeople());
        context.setVariable("totalPrice", newBooking.getTotalPrice());

        BigDecimal totalPrice = newBooking.getTotalPrice();
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        context.setVariable("totalPrice", totalPrice);

        String emailContent = templateEngine.process(confirmation.getTemplate(), context);

        emailService.sendBookingConfirmation(newBooking, emailContent);

        return "redirect:/booking/all";
    }


    @RequestMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/booking/all";
    }

    @RequestMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model){
        BookingDTO booking = bookingService.findBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("pageTitle", "Bokning");
        model.addAttribute("header", "Uppdatera Bokning");
        model.addAttribute("startDateText", "Start Datum");
        model.addAttribute("endDateText", "Slut Datum");
        model.addAttribute("numberOfPeopleText", "Antal personer");
        model.addAttribute("buttonText", "Uppdatera");

        return "booking/update-booking.html";
    }

    @PostMapping("/update")
    public String updateBooking(BookingDTO booking) {
        bookingService.updateBooking(booking);
        return "redirect:/booking/all";
    }

    @RequestMapping("/search")
    public String openAvailableSearchPage(Model model) {
        model.addAttribute("pageTitle", "Sök Lediga Rum");
        model.addAttribute("header", "Sök rum");
        model.addAttribute("startDateText", "Start Datum");
        model.addAttribute("endDateText", "Slut Datum");
        model.addAttribute("numberOfPeopleText", "Antal personer");
        model.addAttribute("submitText", "Sök");

        return "room/search-room.html";
    }
}
