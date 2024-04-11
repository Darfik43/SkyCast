package com.darfik.skycast.weather;

import com.darfik.skycast.commons.models.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
class WeatherResponse extends Response {
    @JsonProperty("name")
    private String locationName;

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;
}
