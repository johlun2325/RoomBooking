package com.example.roombooking.repos;

import com.example.roombooking.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepo extends JpaRepository<RoomType, Long> {
}
