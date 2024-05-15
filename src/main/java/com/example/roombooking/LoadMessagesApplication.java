package com.example.roombooking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@RequiredArgsConstructor
public class LoadMessagesApplication implements CommandLineRunner {

    private String queueName = "3f9ff6e5-cb89-4204-b503-1d9e5e3278bd"; //vårt kö-id

    @Override
    public void run(String... args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            // https://www.baeldung.com/jackson-annotations#bd-jackson-polymorphic-type-handling-annotations
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }


    /*
    Messages looks like
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
