package com.darfik.skycast.services.parsers;

public class WeatherJsonParserFactory implements JsonParserFactory {
    @Override
    public AbstractJsonParser build() {
        return new WeatherJsonParser();
    }
}
