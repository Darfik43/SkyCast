package com.darfik.skycast.services.parsers;

public class WeatherJsonParserFactory extends AbstractJsonParserFactory {


    @Override
    public WeatherJsonParser buildWeatherJsonParser() {
        return new WeatherJsonParser();
    }
}
