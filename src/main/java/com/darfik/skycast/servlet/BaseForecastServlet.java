package com.darfik.skycast.servlet;

import com.darfik.skycast.factory.LocationServiceFactory;
import com.darfik.skycast.factory.WeatherServiceFactory;
import com.darfik.skycast.service.LocationService;
import com.darfik.skycast.service.WeatherService;

public class BaseForecastServlet extends RenderServlet {
    protected LocationService locationService;
    protected WeatherService weatherService;

    @Override
    public void init() {
        this.locationService = LocationServiceFactory.createLocationService();
        this.weatherService = WeatherServiceFactory.createWeatherService();
    }
}
