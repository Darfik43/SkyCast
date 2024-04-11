package com.darfik.skycast.commons.services;

import java.util.Optional;

public interface JsonParser<T> {
    Optional<T> parse(String json);
}
