package com.example.roombooking.controllers;

import com.example.roombooking.dto.BlacklistCustomerDTO;
import com.example.roombooking.services.implementations.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blacklist")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blacklistService = new BlacklistService();

    @GetMapping("/menu")
    String toBlacklistMenu(Model model) {

        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Blacklist");
        model.addAttribute("banButtonText", "Spärra");
        model.addAttribute("updateButtonText", "Uppdatera");

        return "blacklist-menu";
    }

    @GetMapping("/addToBlacklist")
    String toBlacklistBanPage(Model model) {

        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Spärra kund");
        model.addAttribute("emailText", "Fyll i kundens e-postadress");
        model.addAttribute("nameText", "Fyll i kundens fullständiga namn");
        model.addAttribute("isOkText", "OK");
        model.addAttribute("submitButtonText", "Skicka");

        return "blacklist-ban";
    }

    @PostMapping("/ban")
    String addToBlacklist(@RequestParam String email,
                          @RequestParam String name,
                          @RequestParam(required = false) boolean isOk) {

        blacklistService.addCustomerToBlacklist(new BlacklistCustomerDTO(email, name, isOk));
        return "redirect:blacklist-menu";
    }


    @GetMapping("/")
    String updateBlacklistedCustomer(Model model) {


        return "html";
    }


}
