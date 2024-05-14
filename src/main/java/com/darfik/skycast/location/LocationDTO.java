package com.darfik.skycast.location;

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
    private BigDecimal temperature;

    public LocationDTO(String name) {
        this.name = name;
    }
}
