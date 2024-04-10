package com.darfik.skycast.services.parsers;

import java.util.Optional;

public abstract class AbstractJsonParserFactory {

    public abstract WeatherJsonParser buildWeatherJsonParser();
}
