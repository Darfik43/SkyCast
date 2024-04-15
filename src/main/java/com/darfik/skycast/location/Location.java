package com.darfik.skycast.location;

import com.darfik.skycast.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "locations")
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "userid")
    @Getter
    @Setter
    private User user;


    @Column(name = "latitude")
    private BigDecimal latitude;


    @Column(name = "longitude")
    private BigDecimal longitude;

    public Location(String name, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
