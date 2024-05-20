package com.darfik.skycast.util.json;

import com.darfik.skycast.weatherapi.LocationJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class LocationJsonParser {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    public LocationJson[] parse(String json) {
        try {
            LocationJson[] locationJsons = jsonMapper.readValue(json, LocationJson[].class);
            if (locationJsons.length == 0) {
                throw new ArrayIndexOutOfBoundsException("No locations found");
            }
            return locationJsons;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing location JSON", e);
        }
    }
}
