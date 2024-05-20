package com.darfik.skycast.util.json;
import com.darfik.skycast.weatherapi.weather.WeatherJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class WeatherJsonParser {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    public WeatherJson parse(String json) {
        try {
            return jsonMapper.readValue(json, WeatherJson.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing weather JSON", e);
        }
    }
}
