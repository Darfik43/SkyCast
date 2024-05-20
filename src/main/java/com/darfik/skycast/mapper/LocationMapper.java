package com.darfik.skycast.mapper;

import com.darfik.skycast.model.Location;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.weatherapi.LocationJson;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LocationMapper {

    public static Location toModel(LocationDTO locationDTO) {
        return new Location(locationDTO.getName(),
                locationDTO.getLatitude().setScale(2, RoundingMode.HALF_UP),
                locationDTO.getLongitude().setScale(2, RoundingMode.HALF_UP));
    }

    public static LocationDTO toDTO(Location location) {
        return new LocationDTO(location.getName(), location.getLatitude(), location.getLongitude());
    }
    public static LocationDTO fromJsonToDTO(LocationJson locationJson) {
        LocationDTO locationDTO = new LocationDTO();

        if (locationJson != null) {
            locationDTO.setName(locationJson.getName());
            BigDecimal roundedLatitude = locationJson.getLatitude().setScale(2, RoundingMode.HALF_UP);
            locationDTO.setLatitude(roundedLatitude);
            BigDecimal roundedLongitude = locationJson.getLongitude().setScale(2, RoundingMode.HALF_UP);
            locationDTO.setLongitude(roundedLongitude);
            locationDTO.setCountry(locationJson.getCountry());
        }

        return locationDTO;
    }
}
