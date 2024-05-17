package com.example.roombooking;

import com.example.roombooking.models.Events.*;
import com.example.roombooking.repos.EventRepo;
import com.example.roombooking.services.EventService;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Component
@ComponentScan
@RequiredArgsConstructor
public class LoadMessagesApplication implements CommandLineRunner {
    private final EventRepo messageRepo;
    private final EventService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        String queueName = "3f9ff6e5-cb89-4204-b503-1d9e5e3278bd";

        try {
            List<Message> messages = service.fetchEventsFromQueue(queueName);
            messageRepo.saveAll(messages);
            LOGGER.info("Saved " + messages.size() + " messages to database");

        } catch (Exception e) {
            LOGGER.warn("Failed to save messages to database");
            e.printStackTrace();
        }
    }
}
