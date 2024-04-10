package com.darfik.skycast.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherResponse {
    @JsonProperty("name")
    private String locationName;

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;
}
