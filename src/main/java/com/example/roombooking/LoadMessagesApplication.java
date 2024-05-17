package com.example.roombooking;

import com.example.roombooking.models.Events.*;
import com.example.roombooking.repos.EventRepo;
import com.example.roombooking.services.EventService;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@ComponentScan
@RequiredArgsConstructor
public class LoadMessagesApplication implements CommandLineRunner {

//    private String queueName = "3f9ff6e5-cb89-4204-b503-1d9e5e3278bd"; //vårt kö-id
    private final EventRepo messageRepo;
    private final EventService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    String queueName = "3f9ff6e5-cb89-4204-b503-1d9e5e3278bd"; //vårt kö-id

    @Override
    @Transactional
    public void run(String... args) throws Exception {

            try {
                List<Message> messages = service.fetchEventsFromQueue(queueName);
                messageRepo.saveAll(messages);
                LOGGER.info("Saved " + messages.size() + " messages to database");

            } catch (Exception e) {
                LOGGER.warn("Failed to save messages to database");
                e.printStackTrace();
            }


    }


    /*
    Events looks like
 [x] Received '{"type":"RoomClosed","TimeStamp":"2024-05-14T16:16:45.510946154","RoomNo":"10"}'
 [x] Received '{"type":"RoomCleaningFinished","TimeStamp":"2024-05-14T15:28:45.5233706","RoomNo":"5","CleaningByUser":"Devona Larkin PhD"}'
 [x] Received '{"type":"RoomCleaningFinished","TimeStamp":"2024-05-14T14:56:45.541275605","RoomNo":"4","CleaningByUser":"Tia Barton"}'
 [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-15T05:03:45.559865026","RoomNo":"10"}'
 [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-15T02:11:45.579712089","RoomNo":"10"}'
 [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-14T17:14:45.597763349","RoomNo":"2"}'
 [x] Received '{"type":"RoomCleaningFinished","TimeStamp":"2024-05-15T13:09:45.614489342","RoomNo":"8","CleaningByUser":"Caleb McDermott"}'
 [x] Received '{"type":"RoomClosed","TimeStamp":"2024-05-15T09:16:45.638269418","RoomNo":"4"}'
 [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-15T08:48:45.654923436","RoomNo":"2"}'
 [x] Received '{"type":"RoomCleaningFinished","TimeStamp":"2024-05-15T00:57:45.671829536","RoomNo":"3","CleaningByUser":"Erin McDermott"}'
 [x] Received '{"type":"RoomOpened","TimeStamp":"2024-05-15T11:32:45.689579392","RoomNo":"8"}'
    * */


}
