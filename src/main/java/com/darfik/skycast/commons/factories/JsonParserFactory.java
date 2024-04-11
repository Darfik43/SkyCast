package com.darfik.skycast.commons.factories;

import com.darfik.skycast.commons.services.JsonParser;
import com.darfik.skycast.location.LocationJsonParser;
import com.darfik.skycast.weather.WeatherJsonParser;

public abstract class JsonParserFactory {


    public JsonParser build() {
        return buildJsonParser();
    }
    protected abstract JsonParser buildJsonParser();
}
