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

    public void send(String from, String to, String subject, String content) throws MessagingException {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            mimeMessage.setContent(content, "text/html");
            mailSender.send(mimeMessage);
    }
}
