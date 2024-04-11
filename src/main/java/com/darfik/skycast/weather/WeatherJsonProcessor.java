package com.darfik.skycast.weather;

import com.darfik.skycast.commons.factories.JsonParserFactory;
import com.darfik.skycast.commons.services.JsonParser;
import com.darfik.skycast.commons.services.JsonProcessor;

public class WeatherJsonProcessor implements JsonProcessor {

    private final JsonParser<WeatherJson> parser = JsonParserFactory.build("weather");


    private String formatToOutput(WeatherJson weatherJson) {
        return "Current weather in " +
                weatherJson.getLocationName() +
                "<br>" +
                "Temperature: " +
                weatherJson.getMainWeatherData().getTemp() +
                " Celsius<br>" +
                "Feels like: " +
                weatherJson.getMainWeatherData().getFeelsLike() +
                " Celsius<br>";
    }


    public String processJson(String json) {
        WeatherJson responseJson = parser.parse(json);

        return formatToOutput(responseJson);
    }
}
