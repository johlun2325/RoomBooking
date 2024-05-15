package com.example.roombooking.controllers;

import com.example.roombooking.dto.BookingDTO;
import com.example.roombooking.utilities.BlacklistJsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistJsonMapper blacklistJsonMapper = new BlacklistJsonMapper();



    @GetMapping("/")
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

        return "allBookings";
    }


}
