package com.example.roombooking.controllers;

import com.example.roombooking.dto.BlacklistCustomerDTO;
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

        return "blacklist-menu";
    }

    @GetMapping("/addToBlacklist")
    String toBlacklistBanPage(Model model) {

        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Spärra kund");
        model.addAttribute("actionUrl", "ban");
        model.addAttribute("emailText", "Fyll i kundens e-postadress");
        model.addAttribute("showNameField", true);
        model.addAttribute("nameRequired", true);
        model.addAttribute("nameText", "Fyll i kundens fullständiga namn");
        model.addAttribute("isOkText", "OK");
        model.addAttribute("submitButtonText", "Skicka");

        return "blacklist-ban";
    }

    @PostMapping("/ban")
    String addToBlacklist(Model model,
                          @RequestParam String email,
                          @RequestParam String name,
                          @RequestParam(defaultValue = "false") boolean isOk) {

        model.addAttribute("message", blacklistService.addCustomerToBlacklist(new BlacklistCustomerDTO(email, name, isOk)));
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Blacklist");
        model.addAttribute("banButtonText", "Spärra");
        model.addAttribute("updateButtonText", "Uppdatera");

        return "blacklist-menu";
    }

    @GetMapping("/updateToBlacklist")
    String toBlacklistUpdatePage(Model model) {

        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Uppdatera kund");
        model.addAttribute("actionUrl", "update");
        model.addAttribute("emailText", "Fyll i kundens e-postadress");
        model.addAttribute("showNameField", false);
        model.addAttribute("nameRequired", false);
        model.addAttribute("isOkText", "OK");
        model.addAttribute("submitButtonText", "Uppdatera");

        return "blacklist-ban";
    }


    @RequestMapping("/update")
    String updateToBlacklist(Model model,
                             @RequestParam String email,
                             @RequestParam(defaultValue = "false") boolean isOk) {

        model.addAttribute("message", blacklistService.updateCustomerToBlacklist(email, isOk));
        model.addAttribute("pageTitle", "Blacklist");
        model.addAttribute("header", "Blacklist");
        model.addAttribute("banButtonText", "Spärra");
        model.addAttribute("updateButtonText", "Uppdatera");

        return "blacklist-menu";
    }


}
