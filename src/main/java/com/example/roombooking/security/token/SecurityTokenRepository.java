package com.example.roombooking.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityTokenRepository extends JpaRepository<SecurityToken, Long> {

    Optional<SecurityToken> findByToken(String token);
}
