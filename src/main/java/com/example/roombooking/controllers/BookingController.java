package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
class BookingController {

    private final BookingService bookingService;

    @GetMapping("/all")
    String getAllBookings(Model model) {
        List<BookingDTO> all = bookingService.findAllBookings();
        model.addAllAttributes(Map.of(
                "allBookings", all,
                "header", "Alla bokningar",
                "bookingId", "Boknings-Id",
                "customerId", "Kund-Id",
                "roomId", "Rums-Id",
                "roomType", "Rums-typ",
                "nrOfPeople", "Antal personer",
                "delete", "Delete",
                "update", "Update",
                "hem", "Hem"));
        return "allBookings";
    }

    @GetMapping({"/{id}"})
    BookingDTO getAllBookings(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @RequestMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/booking/all";
    }

    // /updateForm/{id}
    @RequestMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model){
        BookingDTO booking = bookingService.findBookingById(id);
        model.addAttribute("booking", booking);
        return "updateBookingForm";
    }

    @PostMapping("/update")
    public String updateBooking(BookingDTO booking) {
        bookingService.updateBooking(booking);
        return "redirect:/booking/all";
    }

    @RequestMapping("/search")
    public String searchBooking() {
        return "searchForm";
    }

}
