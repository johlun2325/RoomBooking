package com.example.roombooking;

import com.example.roombooking.models.EmailConfirmation;
import com.example.roombooking.repos.EmailConfirmationRepo;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import com.example.roombooking.utilities.FileReader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@RequiredArgsConstructor
public class LoadDefaultEmailTemplate implements CommandLineRunner {

    @Autowired
    private final FileReader fileReader;
    private final EmailConfirmationRepo confirmationRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);
    private static final String PATH = "src/main/resources/templates/booking_confirmation_template.html";

    @Override
    public void run(String... args) {
        String content = fileReader.readFile(PATH);
        EmailConfirmation emailConfirmation = new EmailConfirmation(content);
        emailConfirmation.setName("Booking Confirmation Message");

        confirmationRepo.save(emailConfirmation);
        LOGGER.info("Email template saved to database");
    }
}