package com.example.roombooking.repos;

import com.example.roombooking.models.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailConfirmationRepo extends JpaRepository<EmailConfirmation, Long> {

    Optional<EmailConfirmation> findByName(String name);
}
