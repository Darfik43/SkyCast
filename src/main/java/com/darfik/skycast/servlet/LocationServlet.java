package com.darfik.skycast.servlet;


import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.service.LocationService;
import com.darfik.skycast.model.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet("/location")
public class LocationServlet extends RenderServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try {
            LocationService locationService = new LocationService();
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            List<LocationDTO> userLocations = locationService.getUserLocations(userDTO);
            req.getSession().setAttribute("userLocations", userLocations);
        } catch (Exception e) {
            resp.getWriter().print("No locations");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            LocationService locationService = new LocationService();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("location"));
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());

            if ("delete".equals(action)) {
                locationService.deleteLocationForUser(locationDTO, userDTO);
            } else if ("add".equals(action)) {
                locationService.addLocationForUser(locationDTO, userDTO);
            }

            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        } catch (AlreadyAddedLocationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            super.processTemplate("error", req, resp);
        }
    }
}
