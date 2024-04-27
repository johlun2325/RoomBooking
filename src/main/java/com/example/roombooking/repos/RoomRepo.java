package com.example.roombooking.repos;

import com.example.roombooking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {


}
