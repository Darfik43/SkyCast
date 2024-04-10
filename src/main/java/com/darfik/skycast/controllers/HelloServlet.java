package com.darfik.skycast.controllers;

import java.io.*;
import java.net.URISyntaxException;

import com.darfik.skycast.models.WeatherResponse;
import com.darfik.skycast.services.OpenWeatherAPIService;
import com.darfik.skycast.services.WeatherOutputService;
import com.darfik.skycast.services.WeatherProcessingService;
import com.darfik.skycast.services.parsers.WeatherJsonParser;
import com.darfik.skycast.services.parsers.WeatherJsonParserFactory;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private WeatherProcessingService weatherProcessingService;
    private String message;

    public void init() {
        message = "Hello World!";
        weatherProcessingService = new WeatherProcessingService(new WeatherJsonParserFactory());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        String json = null;
        try {
            json = OpenWeatherAPIService.getTemperatureByName("London");
            response.getWriter().print(weatherProcessingService.processJson(json));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }

    public void destroy() {
    }
}