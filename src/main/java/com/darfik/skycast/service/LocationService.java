package com.darfik.skycast.service;

import com.darfik.skycast.dao.LocationDAO;
import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.model.Location;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Log4j2
public class LocationService {
    private final LocationDAO locationDAO;
    private final UserDAO userDAO;
    private final OpenWeatherAPIService openWeatherAPIService;
    private final LocationJsonParser locationParser;
    private final WeatherJsonParser weatherParser;

    public LocationService() {
        this.locationDAO = LocationDAO.getInstance();
        this.openWeatherAPIService = new OpenWeatherAPIService();
        this.userDAO = UserDAO.getInstance();
        this.locationParser = new LocationJsonParser();
        this.weatherParser = new WeatherJsonParser();
    }

    public void addLocationForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException, AlreadyAddedLocationException {
        if (!locationExistsForUser(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(getLocationByCoords(locationDTO));
            userDAO.find(userDTO.getUsername()).ifPresent(location::setUser);
            locationDAO.save(location);
        } else {
            throw new AlreadyAddedLocationException("This location is already added");
        }
    }

    public List<LocationDTO> getUserLocations(UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        List<LocationDTO> userLocations = userDAO.find(userDTO.getUsername())
                .map(user -> locationDAO.findLocationsByUser(user)
                        .stream()
                        .map(LocationMapper::toDTO)
                        .toList())
                .orElse(Collections.emptyList());
        for (LocationDTO location : userLocations) {
            getLocationByCoords(location);
            getWeatherByCoordinates(location);
        }
        return userLocations;
    }

    public boolean locationExistsForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        List<LocationDTO> userLocations = getUserLocations(userDTO);
        return userLocations.stream()
                .anyMatch(userLocation ->
                        userLocation.getName().equals(locationDTO.getName()) &&
                                Objects.equals(userLocation.getLatitude(), locationDTO.getLatitude()) &&
                                Objects.equals(userLocation.getLongitude(), locationDTO.getLongitude())
                );
    }

    public LocationDTO getLocationByCoords(LocationDTO locationDTO) throws IOException, URISyntaxException, InterruptedException {
            LocationJson parsedJson = locationParser.parse(openWeatherAPIService.getLocationByCoords(locationDTO))[0];
            locationDTO.setLatitude(parsedJson.getLatitude());
            locationDTO.setLongitude(parsedJson.getLongitude());
            locationDTO.setCountry(parsedJson.getCountry());
        return locationDTO;
    }

    public LocationDTO[] getLocationsByName(LocationDTO locationDTO) throws IOException, URISyntaxException, InterruptedException {
            LocationJson[] parsedJsons = locationParser.parse(openWeatherAPIService.getLocationsByName(locationDTO.getName()));
            LocationDTO[] locationDTOS = new LocationDTO[parsedJsons.length];
        for (int i = 0; i < locationDTOS.length; i++) {
            locationDTOS[i] = new LocationDTO();
            locationDTOS[i].setName(parsedJsons[i].getLocation());
            locationDTOS[i].setLongitude(parsedJsons[i].getLongitude());
            locationDTOS[i].setLatitude(parsedJsons[i].getLatitude());
            locationDTOS[i].setCountry(parsedJsons[i].getCountry());
        }
        return locationDTOS;
    }

    public LocationDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        try {
            WeatherJson parsedWeatherJson = weatherParser.parse(openWeatherAPIService.getWeatherByCoordinates(locationDTO));
            locationDTO.setTemperature(parsedWeatherJson.getMainWeatherData().getTemp());
            locationDTO.setWeather(parsedWeatherJson.getWeatherData().getFirst().getMain());
            locationDTO.setFeelsLike(parsedWeatherJson.getMainWeatherData().getFeelsLike());
            locationDTO.setIcon(parsedWeatherJson.getWeatherData().getFirst().getIcon());
            return locationDTO;
        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Can't get Weather via OpenWeatherAPI");
        }
        return null;
    }

    public void deleteLocationForUser(LocationDTO locationDTO, UserDTO userDTO) throws IOException, URISyntaxException, InterruptedException {
        if (locationExistsForUser(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(getLocationByCoords(locationDTO));
            userDAO.find(userDTO.getUsername())
                    .ifPresent(user -> {
                        location.setUser(user);
                        locationDAO.delete(location, user);
                    });
        } else {
            throw new IllegalArgumentException("This location isn't added for you");
        }
    }
}
