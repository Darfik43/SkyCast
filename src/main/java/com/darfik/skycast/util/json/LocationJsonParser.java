package com.darfik.skycast.util.json;

import com.darfik.skycast.weatherapi.LocationJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class LocationJsonParser {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    public LocationJson[] parse(String json) {
        try {
            return jsonMapper.readValue(json, LocationJson[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing location JSON", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("No locations found");
        }
    }
}
