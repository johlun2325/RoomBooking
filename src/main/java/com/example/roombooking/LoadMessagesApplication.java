package com.example.roombooking;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.models.Events.Message;
import com.example.roombooking.repos.EventRepo;
import com.example.roombooking.services.EventService;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ComponentScan
@RequiredArgsConstructor
public class LoadMessagesApplication implements CommandLineRunner {
    private final EventRepo messageRepo;
    private final EventService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    @Override
    @Transactional
    public void run(String... args) {

        try {
            List<Message> messages = service.fetchEventsFromQueue();
            messageRepo.saveAll(messages);
            LOGGER.info("Saved {} messages to database", messages.size());

        } catch (Exception e) {
            LOGGER.warn("Failed to save messages to database");
            e.printStackTrace();
        }
    }
}
