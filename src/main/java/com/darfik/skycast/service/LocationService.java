package com.darfik.skycast.service;

import com.darfik.skycast.commons.service.JsonParser;
import com.darfik.skycast.dao.LocationDAO;
import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.model.Location;
import com.darfik.skycast.model.User;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.util.json.LocationJsonParser;
import com.darfik.skycast.util.json.WeatherJsonParser;
import com.darfik.skycast.weatherapi.LocationJson;
import com.darfik.skycast.weatherapi.OpenWeatherAPIService;
import com.darfik.skycast.weatherapi.WeatherJson;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Log4j2
public class LocationService {
    private final LocationDAO locationDAO;
    private final UserDAO userDAO;
    private final OpenWeatherAPIService openWeatherAPIService;
    private final JsonParser<LocationJson> locationParser;
    private final JsonParser<WeatherJson> weatherParser;

    public LocationService() {
        this.locationDAO = LocationDAO.getInstance();
        this.openWeatherAPIService = new OpenWeatherAPIService();
        this.userDAO = UserDAO.getInstance();
        this.locationParser = new LocationJsonParser();
        this.weatherParser = new WeatherJsonParser();
    }

    public void addLocationForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException, AlreadyAddedLocationException {
        if (!locationExistsForUser(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(getLocationByName(locationDTO));
            location.setUser(userDAO.find(userDTO.getUsername()).get());
            locationDAO.save(location);
        } else {
            throw new AlreadyAddedLocationException("This location is already added");
        }
    }

    public List<LocationDTO> getUserLocations(UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        List<LocationDTO> userLocations = locationDAO.findLocationsByUser(userDAO.find(userDTO.getUsername()).get())
                .stream().map(LocationMapper::toDTO)
                .toList();
        for (LocationDTO location : userLocations) {
            getLocationByName(location);
            getWeatherByCoordinates(location);
        }
        return userLocations;
    }

    public boolean locationExistsForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        List<LocationDTO> userLocations = getUserLocations(userDTO);
        return userLocations.stream()
                .map(LocationDTO::getName)
                .anyMatch(locationName -> locationName.equals(locationDTO.getName()));
    }

    public LocationDTO getLocationByName(LocationDTO locationDTO) throws IOException, URISyntaxException, InterruptedException {
            LocationJson parsedJson = locationParser.parse(openWeatherAPIService.getLocationByName(locationDTO.getName()));
            locationDTO.setLatitude(parsedJson.getLatitude());
            locationDTO.setLongitude(parsedJson.getLongitude());
        return locationDTO;
    }

    public LocationDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        try {
            WeatherJson parsedWeatherJson = weatherParser.parse(openWeatherAPIService.getWeatherByCoordinates(locationDTO));
            locationDTO.setTemperature(parsedWeatherJson.getMainWeatherData().getTemp());
            return locationDTO;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Can't get Weather via OpenWeatherAPI");
        }
        return null;
    }

    public void deleteLocationForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        if (locationExistsForUser(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(getLocationByName(locationDTO));
            location.setUser(userDAO.find(userDTO.getUsername()).get());
            User user = userDAO.find(userDTO.getUsername()).get();
            locationDAO.delete(location, user);
        } else {
            throw new IllegalArgumentException("This location isn't added for you");
        }
    }
}
