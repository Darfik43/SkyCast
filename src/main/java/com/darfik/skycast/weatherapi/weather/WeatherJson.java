package com.darfik.skycast.weatherapi.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WeatherJson {

    @JsonProperty("weather")
    private List<ConditionData> conditionData;

    @JsonProperty("main")
    private TemperatureData temperatureData;
}
