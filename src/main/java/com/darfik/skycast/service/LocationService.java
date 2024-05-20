package com.darfik.skycast.service;

import com.darfik.skycast.dao.LocationDAO;
import com.darfik.skycast.dao.UserDAO;
import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.mapper.LocationMapper;
import com.darfik.skycast.mapper.WeatherMapper;
import com.darfik.skycast.model.Location;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.WeatherDTO;
import com.darfik.skycast.util.json.LocationJsonParser;
import com.darfik.skycast.util.json.WeatherJsonParser;
import com.darfik.skycast.weatherapi.LocationJson;
import com.darfik.skycast.weatherapi.OpenWeatherService;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Log4j2
public class LocationService {
    private final LocationDAO locationDAO;
    private final UserDAO userDAO;
    private final OpenWeatherService openWeatherService;
    private final LocationJsonParser locationParser;
    private final WeatherJsonParser weatherParser;

    public LocationService() {
        this.locationDAO = LocationDAO.getInstance();
        this.openWeatherService = new OpenWeatherService();
        this.userDAO = UserDAO.getInstance();
        this.locationParser = new LocationJsonParser();
        this.weatherParser = new WeatherJsonParser();
    }

    public List<LocationDTO> getUserLocations(UserDTO userDTO) {
        return userDAO.find(userDTO.getUsername())
                .map(user -> locationDAO.findLocationsByUser(user)
                        .stream()
                        .map(LocationMapper::toDTO)
                        .peek(locationDTO -> locationDTO.setCountry(getLocationByCoords(locationDTO).getCountry()))
                        .toList())
                .orElse(Collections.emptyList());
    }

    public void addLocationForUser(LocationDTO locationDTO, UserDTO userDTO) throws AlreadyAddedLocationException {
        if (!isAlreadyAdded(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(locationDTO);
            userDAO.find(userDTO.getUsername()).ifPresent(location::setUser);
            locationDAO.save(location);
        } else {
            throw new AlreadyAddedLocationException("This location is already added");
        }
    }

    public void deleteLocationForUser(LocationDTO locationDTO, UserDTO userDTO) {
        if (isAlreadyAdded(locationDTO, userDTO)) {
            Location location = LocationMapper.toModel(locationDTO);
            userDAO.find(userDTO.getUsername())
                    .ifPresent(user -> {
                        location.setUser(user);
                        locationDAO.delete(location, user);
                    });
        }
    }

    public boolean isAlreadyAdded(LocationDTO locationDTO, UserDTO userDTO) {
        return getUserLocations(userDTO).stream()
                .anyMatch(userLocation ->
                                Objects.equals(userLocation.getLatitude(), locationDTO.getLatitude()) &&
                                Objects.equals(userLocation.getLongitude(), locationDTO.getLongitude())
                );
    }

    public LocationDTO getLocationByCoords(LocationDTO locationDTO) {
        return LocationMapper.fromJsonToDTO(
                locationParser.parse(openWeatherService.getLocationByCoords(locationDTO))[0]);
    }

    public LocationDTO[] getLocationsByName(LocationDTO locationDTO) {
        LocationJson[] parsedJsons = locationParser.parse(openWeatherService.getLocationsByName(locationDTO.getName()));
        return Arrays.stream(parsedJsons)
                .map(LocationMapper::fromJsonToDTO)
                .toArray(LocationDTO[]::new);
    }

    public WeatherDTO getWeatherByCoordinates(LocationDTO locationDTO) {
        return WeatherMapper.fromJsonToDTO(
                weatherParser.parse(openWeatherService.getWeatherByCoordinates(locationDTO)));
    }
}
