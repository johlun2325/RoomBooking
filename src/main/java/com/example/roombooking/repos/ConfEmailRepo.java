package com.example.roombooking.repos;

import com.example.roombooking.models.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfEmailRepo extends JpaRepository<Confirmation, Long> {
}
