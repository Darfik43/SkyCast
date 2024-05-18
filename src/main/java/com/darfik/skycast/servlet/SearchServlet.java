package com.darfik.skycast.servlet;

import com.darfik.skycast.model.dto.LocationDTO;
import com.darfik.skycast.model.dto.UserDTO;
import com.darfik.skycast.service.LocationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("/search")
public class SearchServlet extends RenderServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LocationDTO locationDTO = new LocationDTO(req.getParameter("searchQuery"));
            LocationService locationService = new LocationService();

            boolean locationAlreadyAdded = locationService.locationExistsForUser(
                    locationDTO,
                    new UserDTO(req.getSession().getAttribute("username").toString())
            );
            req.setAttribute("locationAlreadyAdded", locationAlreadyAdded);

            locationDTO = locationService.getLocationByName(locationDTO);
            locationDTO = locationService.getWeatherByCoordinates(locationDTO);
            req.setAttribute("location", locationDTO);

            processTemplate("search_results", req, resp);

        } catch (InterruptedException e) {
            resp.getWriter().print("Connection to the API was interrupted");
        } catch (IOException e) {
            resp.getWriter().print("Can't connect to OpenWeatherAPI");
        } catch (URISyntaxException e) {
            resp.getWriter().print("URI is invalid");
        } catch (ArrayIndexOutOfBoundsException e) {
            req.setAttribute("errorMessage", "No locations found");
            processTemplate("search_results", req, resp);
        }
    }
}
