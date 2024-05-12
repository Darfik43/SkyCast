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

@WebServlet("/home")
public class HomeServlet extends RenderServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if ((Boolean) req.getAttribute("isLoggedIn")) {
            try {
                LocationService locationService = LocationServiceFactory.build();
                List<LocationDTO> userLocations = locationService.getUserLocations(new UserDTO(req.getSession().getAttribute("username").toString()));
                req.getSession().setAttribute("userLocations", userLocations);
            } catch (URISyntaxException | InterruptedException e) {
                resp.getWriter().print("Cheto proizoshlo");
            }
        }
        super.processTemplate("home", req, resp);
    }
}
