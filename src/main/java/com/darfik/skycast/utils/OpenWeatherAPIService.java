package com.darfik.skycast.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class OpenWeatherAPIService {

    private static final String API_KEY = getApiKey();

    public static String getLocationByName(String locationName) throws IOException, InterruptedException, URISyntaxException {
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + locationName + "&appid=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getTemperatureByName(String locationName) throws IOException, InterruptedException, URISyntaxException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + locationName + "&appid=" + API_KEY + "&units=metric";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String getApiKey() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
            return properties.getProperty("api.key");
        } catch (IOException e) {
            System.err.println("API key is not available " + e.getMessage());
            return null;
        }
    }
}
