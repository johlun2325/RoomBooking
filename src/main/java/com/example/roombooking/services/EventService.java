package com.example.roombooking.services;

import com.example.roombooking.dto.MessageDTO;
import com.example.roombooking.models.Events.Message;
import java.util.List;

public interface EventService {

    List<Message> fetchEventsFromQueue();

    MessageDTO convertMessagesToDTO(Message message);

    List<String> getAllMessagesByRoomNumber(String roomNo);
}
