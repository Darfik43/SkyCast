package com.darfik.skycast.weatherapi;

import com.darfik.skycast.model.dto.LocationDTO;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class OpenWeatherAPIService {

    private static final String API_KEY = getApiKey();

    public String getLocationByName(String location) throws IOException, InterruptedException, URISyntaxException {
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + location
                + "&appid=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getWeatherByCoordinates(LocationDTO locationDTO) throws IOException, InterruptedException, URISyntaxException {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + locationDTO.getLatitude()
                + "&lon=" + locationDTO.getLongitude()
                + "&appid=" + API_KEY
                + "&units=metric";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String getApiKey() {
        try (InputStream inputStream = OpenWeatherAPIService.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("api.key");
        } catch (IOException e) {
            System.err.println("API key is not available " + e.getMessage());
            return null;
        }
    }
}
