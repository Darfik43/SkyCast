package com.darfik.skycast.location;

public class LocationServiceFactory {
    public static LocationService build() {
        return new LocationService();
    }
}
