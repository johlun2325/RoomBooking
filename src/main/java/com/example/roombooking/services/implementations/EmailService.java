package com.example.roombooking.services.implementations;

import com.example.roombooking.models.Booking;
import com.example.roombooking.utilities.FileReader;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final FileReader fileReader = new FileReader();
    private static final String ETHEREAL_EMAIL = "zion78@ethereal.email";

    public void sendResetPasswordLink(String to, String link) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        String message = fileReader.readFile("src/main/resources/reset_password_template.html");

        helper.setFrom(ETHEREAL_EMAIL);
        helper.setTo(to);
        helper.setSubject("Begäran om Återställning av Lösenord");
        helper.setText(message.formatted(to, link), true);
        mailSender.send(mimeMessage);
    }

    public void sendBookingConfirmation(Booking booking, String message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(ETHEREAL_EMAIL);
            helper.setTo(booking.getCustomer().getEmail());
            helper.setSubject("Bokningsbekräftelse");
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
