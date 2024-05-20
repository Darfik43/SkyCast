package com.darfik.skycast.servlet;

import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.model.dto.WeatherDTO;
import com.darfik.skycast.service.LocationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet("/search")
public class SearchServlet extends RenderServlet {
    private final LocationService locationService = new LocationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LocationDTO[] locationDTOs = locationService.getLocationsByName(
                    new LocationDTO(req.getParameter("searchQuery")));
            for (LocationDTO locationDTO : locationDTOs) {
                locationDTO.setAlreadyAdded(locationService.isAlreadyAdded(
                        locationDTO,
                        new UserDTO(req.getSession().getAttribute("username").toString())
                ));
            }

            WeatherDTO[] weatherDTOs = Arrays.stream(locationDTOs)
                    .map(locationService::getWeatherByCoordinates)
                    .toArray(WeatherDTO[]::new);

            req.setAttribute("foundLocations", locationDTOs);
            req.setAttribute("weatherInLocations", weatherDTOs);

            processTemplate("search_results", req, resp);
        } catch (ArrayIndexOutOfBoundsException e) {
            req.setAttribute("errorMessage", "No locations found");
            processTemplate("search_results", req, resp);
        }
    }
}
