package com.example.roombooking.controllers;

import com.example.roombooking.services.implementations.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SecTestController {

    private final EmailService emailService;

    @GetMapping(path="/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public String empty(Model model) {

        model.addAttribute("activeFunction", "queues");
        model.addAttribute("page", "Admin");
        return "security/admin";
    }

    @GetMapping(path="/receptionist")
    @PreAuthorize("isAuthenticated()")
    public String edit(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();
        model.addAttribute("username", username);

        return "security/profile";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "security/login.html";
    }

    @GetMapping("/password/reset")
    public String forgotPassword(Model model) {
        return "security/reset-password.html";
    }

    @PostMapping("/password/new")
    public String retrievePassword(Model model, @RequestParam String username) throws MessagingException {
        emailService.send("zion78@ethereal.email", username, "Reset Password", "Hej!");

        return "security/login.html";
    }

}
