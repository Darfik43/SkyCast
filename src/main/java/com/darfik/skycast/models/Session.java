package com.darfik.skycast.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;
}
