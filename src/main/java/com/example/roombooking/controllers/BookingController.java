package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.models.Booking;
import com.example.roombooking.services.BookingService;
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


    @GetMapping("/all")
    String getAllBookings(Model model) {
        List<BookingDTO> all = bookingService.getAllBookingDTOs();
        model.addAttribute("allBookings", all);
        model.addAttribute("header", "Alla bokningar");
        model.addAttribute("bookingId", "Boknings-Id");
        model.addAttribute("customerId", "Kund-Id");
        model.addAttribute("roomId", "Rums-Id");
        model.addAttribute("roomType", "Rums-typ");
        model.addAttribute("nrOfPeople", "Antal personer");
        model.addAttribute("delete", "Delete");
        model.addAttribute("update", "Update");
        model.addAttribute("hem", "Hem");
        return "allBookings";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return "redirect:/booking/all";
    }

    ///updateForm/{id}
    @RequestMapping("/updateForm/{id}")
    public String updateByForm(@PathVariable Long id, Model model){
        BookingDTO b = bookingService.findBookingById(id);
        model.addAttribute("booking", b);
        return "updateBookingForm";
    }

    @PostMapping("/update")
    public String updateBooking(BookingDTO booking) {
        bookingService.addBooking(booking);
        return "redirect:/booking/all";
    }


    @GetMapping({"/{id}"})
    BookingDTO getAllBookings(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

    @DeleteMapping({"/delete"})
    BookingDTO deleteCustomer(@PathVariable Long id) {
        return bookingService.findBookingById(id);
    }

}
