package com.darfik.skycast.servlet;

import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.service.LocationService;
import com.darfik.skycast.model.dto.UserDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends RenderServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if ((Boolean) req.getAttribute("isLoggedIn")) {
            try {
                LocationService locationService = new LocationService();
                List<LocationDTO> userLocations = locationService.getUserLocations(new UserDTO(req.getSession().getAttribute("username").toString()));
                req.getSession().setAttribute("userLocations", userLocations);
            } catch (URISyntaxException | InterruptedException e) {
                resp.getWriter().print("Can't get user locations");
            }
        }
        super.processTemplate("home", req, resp);
    }
}
