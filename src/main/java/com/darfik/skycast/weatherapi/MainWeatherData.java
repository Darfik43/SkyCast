package com.darfik.skycast.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherData {

    @JsonProperty("temp")
    private BigDecimal temp;

    @JsonProperty("feels_like")
    private BigDecimal feelsLike;

}
