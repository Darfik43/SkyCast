package com.darfik.skycast;

import com.darfik.skycast.commons.services.JsonProcessor;
import com.darfik.skycast.location.LocationJsonProcessor;
import com.darfik.skycast.weather.WeatherJsonProcessor;

public class ResponseProcessingService {
    private JsonProcessor jsonProcessor;


    public String processJson(String json, String jsonType) {
        setProcessorType(jsonType);
        return jsonProcessor.processJson(json);
    }

    private void setProcessorType(String jsonType) {
        if (jsonType.equals("weather")) {
            this.jsonProcessor = new WeatherJsonProcessor();
        } else if (jsonType.equals("location")) {
            this.jsonProcessor = new LocationJsonProcessor();
        }
    }
}
