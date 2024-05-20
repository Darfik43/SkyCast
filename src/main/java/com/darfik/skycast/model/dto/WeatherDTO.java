package com.darfik.skycast.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeatherDTO {
    private Double temperature;
    private Double feelsLike;
    private String condition;
    private String icon;
}
