package com.darfik.skycast.util.json;
import com.darfik.skycast.commons.service.JsonParser;
import com.darfik.skycast.weatherapi.WeatherJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class WeatherJsonParser implements JsonParser<WeatherJson> {
    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @Override
    public WeatherJson parse(String json) {
        try {
            return jsonMapper.readValue(json, WeatherJson.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing weather JSON", e);
        }
    }
}
