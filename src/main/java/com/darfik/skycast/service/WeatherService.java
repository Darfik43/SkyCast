package com.darfik.skycast.service;

import com.darfik.skycast.mapper.WeatherMapper;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.WeatherDTO;
import com.darfik.skycast.util.json.WeatherJsonParser;
import com.darfik.skycast.weatherapi.OpenWeatherService;

public class WeatherService {
    private final WeatherJsonParser weatherParser;
    private final OpenWeatherService openWeatherService;
    public WeatherService(WeatherJsonParser weatherParser, OpenWeatherService openWeatherService) {
        this.weatherParser = weatherParser;
        this.openWeatherService = openWeatherService;
    }

    public WeatherDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        return WeatherMapper.fromJsonToDTO(
                weatherParser.parse(openWeatherService.getWeatherByCoordinates(locationDTO)));
    }
}
