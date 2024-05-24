package com.example.roombooking.security;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT user FROM User user WHERE user.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    List<User> findAllByUsernameStartingWith(String query, Sort sort);

}
