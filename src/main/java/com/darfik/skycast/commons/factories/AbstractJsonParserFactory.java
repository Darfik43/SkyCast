package com.darfik.skycast.commons.factories;

import com.darfik.skycast.weather.WeatherJsonParser;

public abstract class AbstractJsonParserFactory {

    public abstract WeatherJsonParser buildWeatherJsonParser();
}
