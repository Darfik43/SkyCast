package com.darfik.skycast.weather;

import com.darfik.skycast.commons.factories.AbstractJsonParserFactory;
import com.darfik.skycast.weather.WeatherJsonParser;

class WeatherJsonParserFactory extends AbstractJsonParserFactory {


    @Override
    public WeatherJsonParser buildWeatherJsonParser() {
        return new WeatherJsonParser();
    }
}
