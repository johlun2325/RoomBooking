package com.example.roombooking.repos;

import com.example.roombooking.models.Events.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Message, Long> {
}
