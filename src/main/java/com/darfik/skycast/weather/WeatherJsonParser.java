package com.darfik.skycast.weather;
import com.darfik.skycast.commons.services.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class WeatherJsonParser implements JsonParser<WeatherJson> {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Override
    public WeatherJson parse(String json) {
        try {
            WeatherJson result = jsonMapper.readValue(json, WeatherJson.class);
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing weather JSON", e);
        }
    }
}
