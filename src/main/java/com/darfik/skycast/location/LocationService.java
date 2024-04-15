package com.darfik.skycast.location;

import com.darfik.skycast.commons.services.JsonParser;
import com.darfik.skycast.user.UserDAO;
import com.darfik.skycast.user.UserDTO;
import com.darfik.skycast.utils.OpenWeatherAPIService;
import com.darfik.skycast.weather.WeatherJson;
import com.darfik.skycast.weather.WeatherJsonParser;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Log4j2
public class LocationService {
    private final LocationDAO locationDAO;
    private final UserDAO userDAO;
    private final OpenWeatherAPIService openWeatherAPIService;
    private final JsonParser<LocationJson> locationParser = new LocationJsonParser();
    private final JsonParser<WeatherJson> weatherParser = new WeatherJsonParser();

    LocationService() {
        locationDAO = LocationDAO.getInstance();
        openWeatherAPIService = new OpenWeatherAPIService();
        userDAO = UserDAO.getInstance();
    }

    public LocationDTO addLocationForUser(LocationDTO locationDTO, UserDTO userDTO) {
        Location location;
        locationDTO = getLocationByName(locationDTO);
        location = LocationMapper.toModel(locationDTO);
        location.setUser(userDAO.find(userDTO.getUsername()).get());
        locationDAO.save(location);
        return locationDTO;
    }

    public List<Location> getUserLocations(UserDTO userDTO) {
        return locationDAO.findLocationsByUser(userDAO.find(userDTO.getUsername()).get());
    }

    public LocationDTO getLocationByName(LocationDTO locationDTO) {
        try {
            LocationJson parsedJson = locationParser.parse(openWeatherAPIService.getLocationByName(locationDTO.getName()));
            locationDTO.setLatitude(parsedJson.getLatitude());
            locationDTO.setLongitude(parsedJson.getLongitude());
        } catch (IOException e) {
            log.error("//TODO");
        } catch (InterruptedException e) {
            log.error("//TODO");
        } catch (URISyntaxException e) {
            log.error("//TODO");
        }
        return locationDTO;
    }

    public LocationDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        try {
            WeatherJson parsedWeatherJson = weatherParser.parse(openWeatherAPIService.getWeatherByCoordinates(locationDTO));
            locationDTO.setTemperature(parsedWeatherJson.getMainWeatherData().getTemp());
            return locationDTO;
        } catch (IOException e) {
            log.error("//TODO");
        } catch (InterruptedException e) {
            log.error("//TODO");
        } catch (URISyntaxException e) {
            log.error("//TODO");
        }
        return null;
    }
}
