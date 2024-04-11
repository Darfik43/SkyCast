package com.darfik.skycast.user;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "login")
    @Getter
    @Setter
    private String login;

    @Setter
    @Column(name = "password")
    private String password;

}
