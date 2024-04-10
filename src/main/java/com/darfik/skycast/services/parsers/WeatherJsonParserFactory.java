package com.darfik.skycast.services.parsers;

public class WeatherJsonParserFactory implements JsonParserFactory<WeatherJsonParser> {
    @Override
    public WeatherJsonParser build() {
        return new WeatherJsonParser();
    }
}
