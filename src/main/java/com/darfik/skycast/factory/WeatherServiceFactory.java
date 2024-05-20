package com.darfik.skycast.factory;

import com.darfik.skycast.service.WeatherService;
import com.darfik.skycast.util.json.WeatherJsonParser;
import com.darfik.skycast.weatherapi.OpenWeatherService;

public class WeatherServiceFactory {
    public static WeatherService createWeatherService() {
        WeatherJsonParser weatherJsonParser = new WeatherJsonParser();
        OpenWeatherService openWeatherService = new OpenWeatherService();
        return new WeatherService(weatherJsonParser, openWeatherService);
    }
}
