package com.darfik.skycast.services.parsers;

public interface JsonParserFactory<T extends AbstractJsonParser> {
    T build();
}
