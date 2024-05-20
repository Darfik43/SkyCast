package com.darfik.skycast.factory;

import com.darfik.skycast.dao.LocationDAO;
import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.service.LocationService;
import com.darfik.skycast.util.json.LocationJsonParser;
import com.darfik.skycast.weatherapi.OpenWeatherService;

public class LocationServiceFactory {

    public static LocationService createLocationService() {
        LocationDAO locationDAO = LocationDAO.getInstance();
        UserDAO userDAO = UserDAO.getInstance();
        OpenWeatherService openWeatherService = new OpenWeatherService();
        LocationJsonParser locationJsonParser = new LocationJsonParser();
        return new LocationService(locationDAO, userDAO, openWeatherService, locationJsonParser);
    }
}
