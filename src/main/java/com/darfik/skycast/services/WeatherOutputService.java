package com.darfik.skycast.services;

import com.darfik.skycast.models.WeatherResponse;

public class WeatherOutputService {

    public static String formatWeatherOutput(WeatherResponse weatherResponse) {
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
