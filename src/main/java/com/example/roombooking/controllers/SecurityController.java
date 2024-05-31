package com.example.roombooking.controllers;

import com.example.roombooking.security.token.SecurityTokenService;
import com.example.roombooking.utilities.FileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityTokenService securityTokenService;
    private final FileReader fileReader = new FileReader();
    private static final String BOOKING_CONFIRMATION_FILE = "src/main/resources/templates/booking_confirmation_template.html";

    @GetMapping("/login")
    public String toLogin(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("pageTitle", "Logga in");
        model.addAttribute("header", "Logga in");
        model.addAttribute("usernameLabelText", "E-postadress");
        model.addAttribute("usernamePlaceholder", "email@example.com");
        model.addAttribute("passwordLabelText", "Lösenord");
        model.addAttribute("passwordPlaceholder", "Lösenord");
        model.addAttribute("submitText", "Logga in");
        model.addAttribute("forgotPasswordText", "Glömt Lösenord?");
        model.addAttribute("paramErrorMessage", "Fel inloggningsuppgifter!");

        if (message != null) {
            model.addAttribute("message", message);
        }

        return "security/login.html";
    }

    @GetMapping("/password/request")
    public String toNewPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Lösenord");
        model.addAttribute("header", "Begär nytt lösenord");
        model.addAttribute("usernameLabelText", "E-postadress");
        model.addAttribute("placeholder", "email@example.com");
        model.addAttribute("submitText", "Skicka");

        return "security/password-request.html";
    }

    @PostMapping("/password/send-link")
    @Async
    public CompletableFuture<String> sendPasswordResetRequest(RedirectAttributes redirectAttributes, @RequestParam String username) {
        String message = securityTokenService.resetPasswordRequestConfirmation(username)
                ? "Återställningslänk till lösenord har skickats till användaren '%s'".formatted(username)
                : "Användaren '%s' är inte registrerad.".formatted(username);

        redirectAttributes.addFlashAttribute("message", message);
        return CompletableFuture.completedFuture("redirect:/login");
    }

    @PostMapping("/password/reset")
    public String resetPassword(RedirectAttributes redirectAttributes, @RequestParam String password, @RequestParam String username) {
        securityTokenService.resetPassword(username, password);
        redirectAttributes.addFlashAttribute("message", "Användaren %s har återställt lösenordet.".formatted(username));

        return "redirect:/login";
    }

    @GetMapping("/password/validation")
    public String validateSecurityToken(Model model, RedirectAttributes redirectAttributes, @RequestParam("token") String token) {
        String message = securityTokenService.checkSecurityToken(token);

        if (message.contains("Rejected")) {
            redirectAttributes.addFlashAttribute("message", message.replace("Rejected: ", ""));
            return "redirect:/login";
        }

        model.addAttribute("username", message.replace("Rejected: ", ""));
        return "security/password-reset.html";
    }

    @GetMapping("/email-confirmation")
    public String toBookingConfirmationMessageForm(Model model) {
        String confirmationMessagePlainText = fileReader.extractPlainTextFromFile(BOOKING_CONFIRMATION_FILE);

        model.addAttribute("pageTitle", "Bekräftelsemejl");
        model.addAttribute("header", "Uppdatera Bekräftelsemejl");
        model.addAttribute("confirmationText", "Bekräftelsemejl");
        model.addAttribute("messageContent", confirmationMessagePlainText);
        model.addAttribute("buttonText", "Uppdatera");

        model.addAttribute("oldMessageContent", confirmationMessagePlainText);

        return "security/update-booking-confirmation.html";
    }

    @PostMapping("/update-email-confirmation")
    public String updateConfirmationForm(Model model, @RequestParam String confirmationMessage, @RequestParam String oldMessageContent) {
        fileReader.updateMessagePlainText(BOOKING_CONFIRMATION_FILE, oldMessageContent, confirmationMessage);
        model.addAttribute("message", "Confirmation booking message updated");

        return "index.html";
    }
}