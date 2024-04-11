package com.darfik.skycast.commons.factories;
import com.darfik.skycast.commons.models.ResponseJson;
import com.darfik.skycast.commons.services.JsonParser;
import com.darfik.skycast.location.LocationJsonParser;
import com.darfik.skycast.weather.WeatherJsonParser;

public class JsonParserFactory {
    public static JsonParser<? extends ResponseJson> build(String type) {
        return switch (type) {
            case "weather" -> new WeatherJsonParser();
            case "location" -> new LocationJsonParser();
            default -> null;
        };
    }
}
