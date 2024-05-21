package com.example.roombooking.repos;

import com.example.roombooking.models.Events.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Message, Long> {

    List<Message> findAllByRoomNo(String roomNo);
}
