package com.darfik.skycast.servlet;


import com.darfik.skycast.location.LocationDTO;
import com.darfik.skycast.location.LocationService;
import com.darfik.skycast.location.LocationServiceFactory;
import com.darfik.skycast.user.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet("/location")
public class LocationServlet extends RenderServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LocationService locationService = LocationServiceFactory.build();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("location"));
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            locationService.deleteLocationForUser(locationDTO, userDTO);
        } catch (InterruptedException e) {
        resp.getWriter().print("Connection to the API was interrupted");
    } catch (IOException e) {
        resp.getWriter().print("Can't connect to OpenWeatherAPI");
    } catch (URISyntaxException e) {
        resp.getWriter().print("URI is invalid");
    }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LocationService locationService = LocationServiceFactory.build();
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            List<LocationDTO> userLocations = locationService.getUserLocations(userDTO);
            req.getSession().setAttribute("userLocations", userLocations);
        } catch (Exception e) {
            resp.getWriter().print("No locations");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LocationService locationService = LocationServiceFactory.build();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("location"));
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            locationService.addLocationForUser(locationDTO, userDTO);
        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        }
    }
}
