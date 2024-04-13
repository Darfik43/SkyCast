package com.darfik.skycast.location;

import com.darfik.skycast.commons.services.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class LocationJsonParser implements JsonParser<LocationJson> {

    private final JsonMapper jsonMapper = JsonMapper.builder().build();
    @Override
    public LocationJson parse(String json) {
        try {
            return jsonMapper.readValue(json, LocationJson[].class)[0];
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing location JSON", e);
        }
    }
}
