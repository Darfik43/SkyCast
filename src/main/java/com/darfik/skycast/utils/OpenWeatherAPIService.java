package com.darfik.skycast.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OpenWeatherAPIService {
    private static final String API_KEY = "886c310d5e5f5a4504aba96e6c34e833";

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
}
