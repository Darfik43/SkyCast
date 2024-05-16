package com.darfik.skycast.commons.service;


public interface JsonParser<T> {
    T parse(String json);
}
