package com.darfik.skycast.model;

import com.darfik.skycast.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sessions")
public class UserSession {
    public UserSession(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "id")
    private String id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "expires_at")
    private LocalDateTime expiresAt;


}
