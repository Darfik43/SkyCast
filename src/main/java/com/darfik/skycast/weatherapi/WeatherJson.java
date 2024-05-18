package com.darfik.skycast.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherJson {
    @JsonProperty("name")
    private String location;

    @JsonProperty("weather")
    private List<WeatherData> weatherData;

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;
}
