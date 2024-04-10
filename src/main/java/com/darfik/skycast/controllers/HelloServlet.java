package com.darfik.skycast.controllers;

import java.io.*;
import java.net.URISyntaxException;

import com.darfik.skycast.daos.UserDAO;
import com.darfik.skycast.models.User;
import com.darfik.skycast.services.OpenWeatherAPIService;
import com.darfik.skycast.utils.HibernateUtil;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.Session;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {


    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String locationName = "London";
        try {
            String weatherData = OpenWeatherAPIService.getTemperatureByName(locationName);
            response.getWriter()
                    .print(
                            "Hello\n" + "Current weather in "
                                    + locationName + " "
                                    + weatherData
                    );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}