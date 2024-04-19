package com.darfik.skycast.servlet;

import com.darfik.skycast.location.LocationDTO;
import com.darfik.skycast.location.LocationService;
import com.darfik.skycast.location.LocationServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/search")
public class SearchServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            LocationService locationService = LocationServiceFactory.build();
            LocationDTO locationDTO = new LocationDTO();
            locationDTO.setName("London");
            locationDTO = locationService.getLocationByName(locationDTO);
            resp.getWriter().print(locationDTO.getName() + " " + locationDTO.getLatitude() + " " +
                    locationDTO.getLongitude() + " " + locationDTO.getTemperature());
        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        }
    }
}
