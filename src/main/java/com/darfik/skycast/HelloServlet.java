package com.darfik.skycast;

import java.io.*;
import java.net.URISyntaxException;

import com.darfik.skycast.user.UserDAO;
import com.darfik.skycast.user.User;
import com.darfik.skycast.utils.OpenWeatherAPIService;
import com.darfik.skycast.weather.WeatherProcessingService;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private WeatherProcessingService weatherProcessingService;
    private String message;

    public void init() {
        message = "Hello World!";
        weatherProcessingService = new WeatherProcessingService();
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