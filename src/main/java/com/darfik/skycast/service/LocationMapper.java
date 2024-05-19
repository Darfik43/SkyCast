package com.darfik.skycast.service;

import com.darfik.skycast.model.Location;
import com.darfik.skycast.model.dto.LocationDTO;

public class LocationMapper {

    public static Location toModel(LocationDTO locationDTO) {
        return new Location(locationDTO.getName(), locationDTO.getLatitude(), locationDTO.getLongitude());
    }

    public static LocationDTO toDTO(Location location) {
        return new LocationDTO(location.getName(), location.getLatitude(), location.getLongitude());
    }
}
