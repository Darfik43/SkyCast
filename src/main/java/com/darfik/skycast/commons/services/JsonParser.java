package com.darfik.skycast.commons.services;


import com.darfik.skycast.commons.models.Response;

public interface JsonParser {
    Response parse(String json);
}
