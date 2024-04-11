package com.darfik.skycast.weather;

import com.darfik.skycast.commons.factories.JsonParserFactory;
import com.darfik.skycast.commons.services.JsonParser;

class WeatherJsonParserFactory extends JsonParserFactory {
    @Override
    protected JsonParser buildJsonParser() {
        return new WeatherJsonParser();
    }
}
