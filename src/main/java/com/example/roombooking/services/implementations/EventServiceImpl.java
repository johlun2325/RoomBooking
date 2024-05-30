package com.example.roombooking.services.implementations;

import com.example.roombooking.configurations.IntegrationProperties;
import com.example.roombooking.dto.MessageDTO;
import com.example.roombooking.models.Events.Message;
import com.example.roombooking.repos.EventRepo;
import com.example.roombooking.services.EventService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepo repo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);

    @Autowired
    IntegrationProperties integrationProperties;


    @Override
    public List<Message> fetchEventsFromQueue() {
        LOGGER.info("Starting to fetch contract customers from external service.");

        List<Message> messages = new ArrayList<>();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrationProperties.getEvent().getHost());
        factory.setUsername(integrationProperties.getEvent().getUsername());
        factory.setPassword(integrationProperties.getEvent().getPassword());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8); // "UTF-8"
                System.out.println(" [x] Received '" + message + "'");

                Message msg = mapper.readValue(message, Message.class);
                messages.add(msg);

            };

            channel.basicConsume(integrationProperties.getEvent().getQueue(), true, deliverCallback, consumerTag -> {

            });

            Thread.sleep(5000);
            return messages;

        } catch (IOException | TimeoutException e) {
            LOGGER.error("An unexpected error occurred when fetching events", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public MessageDTO convertMessagesToDTO(Message message) {

        return MessageDTO.builder()
                .id(message.getId())
                .roomNo(message.getRoomNo())
                .timeStamp(message.getTimeStamp())
                .message(message.getMessage()).build();
    }

    @Override
    public List<String> getAllMessagesByRoomNumber(String roomNo) {

        List<Message> messages = repo.findAllByRoomNo(roomNo);

        if (messages.isEmpty()) {
            return List.of("Inga meddelanden för rum " + roomNo);
        }

        return messages.stream().map(Message::getMessage).toList();
    }
}
