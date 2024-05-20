package com.darfik.skycast.service;

import com.darfik.skycast.mapper.WeatherMapper;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.WeatherDTO;
import com.darfik.skycast.util.json.WeatherJsonParser;
import com.darfik.skycast.weatherapi.OpenWeatherService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeatherService {
    private final WeatherJsonParser weatherParser;
    private final OpenWeatherService openWeatherService;

    public WeatherDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        return WeatherMapper.fromJsonToDTO(
                weatherParser.parse(openWeatherService.getWeatherByCoordinates(locationDTO)));
    }
}
