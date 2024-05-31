package com.example.roombooking.security.token;

import com.example.roombooking.security.User;
import com.example.roombooking.security.UserRepository;
import com.example.roombooking.services.implementations.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityTokenService {

    private final UserRepository userRepository;
    private final SecurityTokenRepository securityTokenRepository;
    private final EmailSenderService emailSenderService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityTokenService.class);

    public boolean resetPasswordRequestConfirmation(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            LOGGER.warn("The username '{}' is not registered.", username);
            return false;
        }

        User foundUser = userOptional.get();
        String token = UUID.randomUUID().toString();
        String link = "http://localhost:8080/password/validation?token=" + token;
        SecurityToken securityToken = new SecurityToken(token,
                                                        LocalDateTime.now(),
                                                        LocalDateTime.now().plusHours(24),
                                                        foundUser);
        securityTokenRepository.save(securityToken);
        try {
            emailSenderService.sendResetPasswordLink(username, link);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset-password email", e);
        }
    }

    public String checkSecurityToken(String token) {
        var securityToken = securityTokenRepository.findByToken(token)
                .orElseThrow(NoSuchElementException::new);

        if (securityToken.getConfirmedAt() != null) {
            LOGGER.warn("Security token has already been used.");
            return "Rejected: Återställningslänken för lösenord har redan använts.";
        }

        securityToken.setConfirmedAt(LocalDateTime.now());
        securityTokenRepository.save(securityToken);

        if (securityToken.notExpired()) {
            LOGGER.info("Security token has been confirmed");
            return securityToken.getUser().getUsername();
        } else {
            LOGGER.warn("Security token has expired.");
            return "Rejected: Token har gått ut. Vänligen begär en ny återställning av lösenordet.";
        }
    }

    public void resetPassword(String username, String password) {
        userRepository.findByUsername(username).ifPresentOrElse(foundUser -> {
            foundUser.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(foundUser);
            LOGGER.info("User with username: %s password updated".formatted(foundUser.getUsername()));
        }
        , () -> LOGGER.warn("User with username: %s not found".formatted(username)));
    }
}
