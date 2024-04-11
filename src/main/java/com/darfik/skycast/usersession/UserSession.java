package com.darfik.skycast.usersession;

import com.darfik.skycast.user.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class UserSession {

    @Id
    @Setter
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;
}
