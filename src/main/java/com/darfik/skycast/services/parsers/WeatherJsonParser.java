package com.darfik.skycast.services.parsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;

public class WeatherJsonParser extends AbstractJsonParser {

    @Override
    public <T> Optional<T> parse(String json, Class<T> clazz) {
        try {
            T result = jsonMapper.readValue(json, clazz);
            return Optional.ofNullable(result);
        } catch (JsonProcessingException e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            return Optional.empty();
        }
    }
}
