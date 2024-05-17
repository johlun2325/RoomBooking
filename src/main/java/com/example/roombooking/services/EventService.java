package com.example.roombooking.services;

import com.example.roombooking.models.Events.Message;

import java.util.List;

public interface EventService {

    List<Message> fetchEventsFromQueue(String queueName);
}
