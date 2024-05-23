package com.example.roombooking.security;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT user FROM User user WHERE user.username = :username")
    User getUserByUsername(@Param("username") String username);

}
