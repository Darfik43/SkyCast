package com.darfik.skycast.commons.services;


import com.darfik.skycast.commons.models.ResponseJson;

public interface JsonParser<T extends ResponseJson> {
    T parse(String json);
}
