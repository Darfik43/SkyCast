package com.darfik.skycast.services;

import com.darfik.skycast.models.WeatherResponse;
import com.darfik.skycast.services.parsers.JsonParserFactory;
import com.darfik.skycast.services.parsers.WeatherJsonParser;

import java.util.Optional;

public class WeatherProcessingService {
    private final JsonParserFactory<WeatherJsonParser> parserFactory;

    public WeatherProcessingService(JsonParserFactory<WeatherJsonParser> parserFactory) {
        this.parserFactory = parserFactory;
    }

    public String processJson(String json) {
        WeatherJsonParser parser = parserFactory.build();

        Optional<WeatherResponse> weatherResponseOptional = parser.parse(json, WeatherResponse.class);
        if (weatherResponseOptional.isPresent()) {
            WeatherResponse weatherResponse = weatherResponseOptional.get();
            return formatToWeatherOutput(weatherResponse);
        } else {
            return "There's nothing to parse";
        }
    }

    private String formatToWeatherOutput(WeatherResponse weatherResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current weather in ")
                .append(weatherResponse.getLocationName())
                .append("<br>");
        stringBuilder.append("Temperature: ")
                .append(weatherResponse.getMainWeatherData().getTemp())
                .append(" Celsius<br>");
        stringBuilder.append("Feels like: ")
                .append(weatherResponse.getMainWeatherData().getFeelsLike())
                .append(" Celsius<br>");

        return stringBuilder.toString();
    }

}
