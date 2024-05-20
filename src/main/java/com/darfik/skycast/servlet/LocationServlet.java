package com.darfik.skycast.servlet;


import com.darfik.skycast.enums.SkycastURL;
import com.darfik.skycast.exception.AlreadyAddedLocationException;
import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/location")
public class LocationServlet extends BaseForecastServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());
            List<LocationDTO> userLocations = locationService.getUserLocations(userDTO);
            req.getSession().setAttribute("userLocations", userLocations);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            LocationDTO locationDTO = new LocationDTO(req.getParameter("location"),
                    new BigDecimal(req.getParameter("latitude")),
                    new BigDecimal(req.getParameter("longitude")));
            UserDTO userDTO = new UserDTO(req.getSession().getAttribute("username").toString());

            switch (action) {
                case "delete" -> locationService.deleteLocationForUser(locationDTO, userDTO);
                case "add" -> locationService.addLocationForUser(locationDTO, userDTO);
            }

            resp.sendRedirect(req.getContextPath() + SkycastURL.HOME_URL.getValue());
        }  catch (AlreadyAddedLocationException e) {
            req.setAttribute("errorMessage", e.getMessage());
            super.processTemplate("error", req, resp);
        }
    }
}
