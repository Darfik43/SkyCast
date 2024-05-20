package com.darfik.skycast.servlet;


import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.service.LocationService;
import com.darfik.skycast.model.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet("/location")
public class LocationServlet extends RenderServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            LocationService locationService = new LocationService();
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            List<LocationDTO> userLocations = locationService.getUserLocations(userDTO);
            req.getSession().setAttribute("userLocations", userLocations);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            LocationService locationService = new LocationService();
            LocationDTO locationDTO = new LocationDTO(req.getParameter("location"),
                    new BigDecimal(req.getParameter("latitude")),
                    new BigDecimal(req.getParameter("longitude")));
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());

            if ("delete".equals(action)) {
                locationService.deleteLocationForUser(locationDTO, userDTO);
            } else if ("add".equals(action)) {
                locationService.addLocationForUser(locationDTO, userDTO);
            }

            resp.sendRedirect(req.getContextPath() + "/home");
        }  catch (AlreadyAddedLocationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            super.processTemplate("error", req, resp);
        }
    }
}
