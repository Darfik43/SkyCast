package com.darfik.skycast.location;

import com.darfik.skycast.ResponseProcessingService;
import com.darfik.skycast.utils.OpenWeatherAPIService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet(value = "/location-servlet")
public class LocationServlet extends HttpServlet {
    private ResponseProcessingService responseProcessingService;

    public void init() {
        responseProcessingService = new ResponseProcessingService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json;
        try {
            json = OpenWeatherAPIService.getLocationByName("London");
            String jsonType = "location";
            response.getWriter().print(responseProcessingService.processJson(json, jsonType));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
