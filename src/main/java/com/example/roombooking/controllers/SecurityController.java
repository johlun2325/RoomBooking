package com.example.roombooking.controllers;

import com.example.roombooking.security.token.ConfirmationTokenRepository;
import com.example.roombooking.services.implementations.EmailService;
import com.example.roombooking.services.implementations.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Logga in");
        model.addAttribute("header", "Logga in");
        model.addAttribute("usernameLabelText", "E-postadress");
        model.addAttribute("usernamePlaceholder", "email@example.com");
        model.addAttribute("passwordLabelText", "Lösenord");
        model.addAttribute("passwordPlaceholder", "Hemligt");
        model.addAttribute("submitText", "Logga in");
        model.addAttribute("forgotPasswordText", "Glömt Lösenord?");

        return "security/login.html";
    }

    @GetMapping("/password/request")
    public String forgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Lösenord");
        model.addAttribute("header", "Begär nytt lösenord");
        model.addAttribute("usernameLabelText", "E-postadress");
        model.addAttribute("placeholder", "email@example.com");
        model.addAttribute("usernameRepeatLabelText", "Upprepa e-postadress");
        model.addAttribute("plasubmitTextceholder", "Nytt lösenord");

        return "security/password-request.html";
    }

    @PostMapping("/password/send-link")
    @Async
    public CompletableFuture<String> sendResetPasswordLink(Model model, @RequestParam String username) throws MessagingException {

        final String link =  userService.requestPasswordReset(username);
        if (!link.equalsIgnoreCase("failed")) {
            emailService.send(username, link);
        }

        // TODO: The requestPasswordReset() will return a String. Check the string if error-message is returned (user not found)

        return CompletableFuture.completedFuture("security/login.html");
    }

    @PostMapping("/password/reset")
    public String resetPassword(Model model, @RequestParam String password, @RequestParam String username) {

        userService.resetPassword(username, password);

        return "security/login.html";
    }

    @GetMapping("/password/validation")
    public String validateToken(Model model, @RequestParam("token") String token) {
        var confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(NoSuchElementException::new);
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);

        String page;
        if (confirmationToken.notExpired()) {
            // TODO: Model user to the page
            model.addAttribute("username", confirmationToken.getUser().getUsername());
            page = "security/password-reset.html";
        } else {
            // TODO: We need to send in the "expired time" token message with Model
            page = "security/login.html";
        }

        return page;
    }
}
