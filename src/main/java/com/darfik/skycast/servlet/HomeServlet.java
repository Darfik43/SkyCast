package com.darfik.skycast.servlet;

import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.WeatherDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends BaseForecastServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ((Boolean) req.getAttribute("isLoggedIn")) {
            List<LocationDTO> locationDTOs = locationService.getUserLocations(
                    new UserDTO(req.getSession().getAttribute("username").toString()));

            WeatherDTO[] weatherDTOs = locationDTOs.stream()
                    .map(weatherService::getWeatherByCoordinates)
                    .toArray(WeatherDTO[]::new);

            req.setAttribute("userLocations", locationDTOs);
            req.setAttribute("weatherInLocations", weatherDTOs);
        }

        super.processTemplate("home", req, resp);
    }
}
