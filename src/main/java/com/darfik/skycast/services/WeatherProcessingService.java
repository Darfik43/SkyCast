package com.darfik.skycast.services;

import com.darfik.skycast.models.WeatherResponse;
import com.darfik.skycast.services.parsers.AbstractJsonParserFactory;
import com.darfik.skycast.services.parsers.WeatherJsonParser;
import com.darfik.skycast.services.parsers.WeatherJsonParserFactory;

import java.util.Optional;

public class WeatherProcessingService {
    private AbstractJsonParserFactory builder = new WeatherJsonParserFactory();
    private WeatherJsonParser parser;

    public WeatherProcessingService() {
        parser = builder.buildWeatherJsonParser();
    }

    public String processJson(String json) {
        Optional<WeatherResponse> weatherResponseOptional = parser.parse(json);

        return weatherResponseOptional.map(this::formatToWeatherOutput)
                .orElse("There's nothing to parse");
    }

    private String formatToWeatherOutput(WeatherResponse weatherResponse) {
        //TODO
        return "Current weather in " +
                weatherResponse.getLocationName() +
                "<br>" +
                "Temperature: " +
                weatherResponse.getMainWeatherData().getTemp() +
                " Celsius<br>" +
                "Feels like: " +
                weatherResponse.getMainWeatherData().getFeelsLike() +
                " Celsius<br>";
    }

}
