package com.example.roombooking.services.implementations;

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

    private static final String ETHEREAL_EMAIL = "zion78@ethereal.email";
    private static final String SUBJECT = "Begäran om Återställning av Lösenord";
    private static final String MESSAGE_TEMPLATE = """
        <p>Hej %s,</p>
        
        <p>Vi har mottagit en begäran om att återställa lösenordet för ditt konto.</p>
        <p>Du kan återställa ditt lösenord genom att klicka på länken nedan.<br>
        Observera att denna länk kommer att förfalla om <strong>24 timmar</strong> och att den endast kan användas <strong>en gång</strong>.</p>
        
        <p><a href="%s">Återställ ditt lösenord</a></p>
        
        <p>Om du inte har begärt en återställning av lösenordet, vänligen ignorera detta e-postmeddelande och kontakta support.</p>
        
        <p>Med vänliga hälsningar</p>
        <p>RoomBooking</p>
        """;

    public void sendResetPasswordLink(String to, String link) throws MessagingException {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(ETHEREAL_EMAIL);
            helper.setTo(to);
            helper.setSubject(SUBJECT);
            helper.setText(MESSAGE_TEMPLATE.formatted(to, link), true);
            mailSender.send(mimeMessage);
    }
}
