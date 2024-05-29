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
    private static final String SUBJECT = "Reset Password";

    public void send(String to, String content) throws MessagingException {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(ETHEREAL_EMAIL);
            helper.setTo(to);
            helper.setSubject(SUBJECT);
            mimeMessage.setContent(content, "text/html");
            mailSender.send(mimeMessage);
    }
}
