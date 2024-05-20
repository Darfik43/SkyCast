package com.darfik.skycast.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String country;
    private boolean alreadyAdded;

    public LocationDTO(String name) {
        this.name = name;
    }
    public LocationDTO(String name, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
