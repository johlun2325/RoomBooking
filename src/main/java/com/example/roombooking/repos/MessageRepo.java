package com.example.roombooking.repos;

import com.example.roombooking.models.Messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
