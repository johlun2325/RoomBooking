package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.dto.CustomerLiteDTO;
import com.example.roombooking.dto.RoomLiteDTO;
import com.example.roombooking.services.BookingService;
import com.example.roombooking.utilities.DateStrategy;
import com.example.roombooking.utilities.DateUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;
    private final DateUtility dateUtility = new DateStrategy();

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
                             @RequestParam double roomPrice) {

        BookingDTO bookingDTO = new BookingDTO(new CustomerLiteDTO(ssn),
                                               new RoomLiteDTO(roomId, roomPrice),
                                               numberOfPeople,
                                               dateUtility.convertToLocalDate(startDate),
                                               dateUtility.convertToLocalDate(endDate));
        bookingService.addBooking(bookingDTO);
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
