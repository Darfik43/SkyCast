package com.darfik.skycast.usersession;

import com.darfik.skycast.user.User;
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
    @JoinColumn(name = "userId")
    private User user;


    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;


}
