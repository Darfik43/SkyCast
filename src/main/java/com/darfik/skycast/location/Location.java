package com.darfik.skycast.location;

import com.darfik.skycast.user.User;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Setter
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;
}
