package com.darfik.skycast.models;


import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Getter
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
