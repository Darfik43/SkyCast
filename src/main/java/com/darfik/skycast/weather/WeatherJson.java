package com.darfik.skycast.weather;

import com.darfik.skycast.commons.models.ResponseJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherJson extends ResponseJson {
    @JsonProperty("name")
    private String locationName;

    @JsonProperty("main")
    private MainWeatherData mainWeatherData;
}
