package com.darfik.skycast.weatherapi;

import com.darfik.skycast.model.dto.LocationDTO;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
@Log4j2
public class OpenWeatherService {

    private static final String API_KEY = getApiKey();

    private String executeRequest(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.debug("Error processing request to OpenWeatherAPI");
        }
        return null;
    }

    public String getLocationsByName(String location){
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + location
                + "&limit=5"
                + "&appid=" + API_KEY;

        return executeRequest(url);
    }

    public String getLocationByCoords(LocationDTO locationDTO) {
        String url = "https://api.openweathermap.org/geo/1.0/reverse?"
                + "lat=" + locationDTO.getLatitude()
                + "&lon=" + locationDTO.getLongitude()
                + "&appid=" + API_KEY;

        return executeRequest(url);
    }

    public String getWeatherByCoordinates(LocationDTO locationDTO) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + locationDTO.getLatitude()
                + "&lon=" + locationDTO.getLongitude()
                + "&appid=" + API_KEY
                + "&units=metric";

        return executeRequest(url);
    }



    private static String getApiKey() {
        try (InputStream inputStream = OpenWeatherService.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("api.key");
        } catch (IOException e) {
            System.err.println("API key is not available " + e.getMessage());
            return null;
        }
    }
}
