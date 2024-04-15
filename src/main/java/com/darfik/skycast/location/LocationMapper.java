package com.darfik.skycast.location;

public class LocationMapper {

    public static Location toModel(LocationDTO locationDTO) {
        return new Location(locationDTO.getName(), locationDTO.getLatitude(), locationDTO.getLongitude());
    }

    public static LocationDTO toDTO(Location location) {
        return new LocationDTO(location.getName());
    }
}
