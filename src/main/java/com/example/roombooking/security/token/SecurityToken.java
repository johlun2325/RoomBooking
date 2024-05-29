package com.example.roombooking.security.token;

import com.example.roombooking.security.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SecurityToken {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public SecurityToken(String token,
                         LocalDateTime createdAt,
                         LocalDateTime expiresAt,
                         User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public boolean notExpired() {
        if (confirmedAt == null)
            throw new RuntimeException("Token has not yet been confirmed.");

        return confirmedAt.isBefore(expiresAt);
    }
}
