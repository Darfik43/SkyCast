package com.darfik.skycast.mapper;

import com.darfik.skycast.model.dto.WeatherDTO;
import com.darfik.skycast.weatherapi.weather.WeatherJson;

public class WeatherMapper {
    public static WeatherDTO fromJsonToDTO(WeatherJson weatherJson) {
        WeatherDTO weatherDTO = new WeatherDTO();

        if (weatherJson != null) {
            weatherDTO.setTemperature(weatherJson.getTemperatureData().getTemp());
            weatherDTO.setFeelsLike(weatherJson.getTemperatureData().getFeelsLike());
            weatherDTO.setCondition(weatherJson.getConditionData().getFirst().getMain());
            weatherDTO.setIcon(weatherJson.getConditionData().getFirst().getIcon());
        }

        return weatherDTO;
    }


}
