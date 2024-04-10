package com.darfik.skycast.services.parsers;

import java.util.Optional;

public interface JsonParser<T> {
    Optional<T> parse(String json);
}
