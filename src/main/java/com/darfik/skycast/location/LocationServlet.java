package com.darfik.skycast.location;

import com.darfik.skycast.ResponseProcessingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/location-servlet")
public class LocationServlet extends HttpServlet {
    private ResponseProcessingService responseProcessingService;
    private final LocationService locationService;

    public LocationServlet() {
        locationService = LocationServiceFactory.build();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName(req.getParameter("locationName"));
        locationDTO = locationService.getLocationByName(locationDTO);
        resp.getWriter().print(locationDTO.getName() + " " + locationDTO.getLatitude() + " " +
                locationDTO.getLongitude() + " " + locationDTO.getTemperature());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


}
