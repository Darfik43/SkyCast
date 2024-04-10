package com.darfik.skycast.services.parsers;

import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.Optional;

public abstract class AbstractJsonParser {
    protected JsonMapper jsonMapper;
    public AbstractJsonParser() {
        this.jsonMapper = JsonMapper.builder().build();
    }
    public abstract <T> Optional<T> parse(String json, Class<T> clazz);
}
