package com.example.roombooking.controllers;

import com.example.roombooking.dto.BlacklistedCustomerDTO;
import com.example.roombooking.services.implementations.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        return "blacklist/blacklist-menu.html";
    }

    @GetMapping("/addToBlacklist")
    String toBlacklistBanPage(Model model) {
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Spärra kund");
        model.addAttribute("actionUrl", "ban");
        model.addAttribute("emailText", "Fyll i kundens e-postadress");
        model.addAttribute("nameText", "Fyll i kundens fullständiga namn");
        model.addAttribute("updatingStatus", false);
        model.addAttribute("submitButtonText", "Skicka");

        return "blacklist/blacklist-form.html";
    }

    @PostMapping("/ban")
    String addToBlacklist(Model model,
                          @RequestParam String email,
                          @RequestParam String name,
                          @RequestParam(defaultValue = "false") boolean isOk) {
        model.addAttribute("message", blacklistService.addCustomerToBlacklist(
                new BlacklistedCustomerDTO(email, name, isOk)));
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Blacklist");
        model.addAttribute("banButtonText", "Spärra");
        model.addAttribute("updateButtonText", "Uppdatera");

        return "blacklist/blacklist-menu.html";
    }

    @GetMapping("/updateToBlacklist")
    String toBlacklistUpdatePage(Model model) {
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Uppdatera kund");
        model.addAttribute("actionUrl", "update");
        model.addAttribute("emailText", "Fyll i kundens e-postadress");
        model.addAttribute("nameText", "Fyll i kundens fullständiga namn");
        model.addAttribute("isOkText", "OK");
        model.addAttribute("updatingStatus", true);
        model.addAttribute("submitButtonText", "Uppdatera");

        return "blacklist/blacklist-form.html";
    }


    @RequestMapping("/update")
    String updateToBlacklist(Model model,
                             @RequestParam String email,
                             @RequestParam String name,
                             @RequestParam(defaultValue = "false") boolean isOk) {
        model.addAttribute("message", blacklistService.updateCustomerToBlacklist(
                new BlacklistedCustomerDTO(email, name, isOk)));
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Blacklist");
        model.addAttribute("banButtonText", "Spärra");
        model.addAttribute("updateButtonText", "Uppdatera");

        return "blacklist/blacklist-menu.html";
    }


}
